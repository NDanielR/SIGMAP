"""
Módulo: limpieza_ventas_oop.py

Responsabilidad:
    Limpiar la tabla de análisis de ventas y dejarla en formato ideal para Power BI.

Idea principal:
    Este archivo contiene una clase llamada LimpiadorVentas.
    La clase recibe la ruta del Excel de ventas, la ruta de la tabla maestra y una carpeta de salida.

Uso desde notebook:
    from limpieza_ventas_oop import LimpiadorVentas

    limpiador = LimpiadorVentas(
        ruta_ventas="../Data/Tabla Analisis de ventas.xlsx",
        ruta_maestro="../Data/Tabla_Maestra_Productos.xlsx",
        carpeta_salida="../Resultado"
    )

    archivo_final = limpiador.ejecutar()

Uso desde consola:
    python limpieza_ventas_oop.py --ventas "../Data/Tabla Analisis de ventas.xlsx" --maestro "../Data/Tabla_Maestra_Productos.xlsx" --salida "../Resultado"
"""

from __future__ import annotations

import argparse
from pathlib import Path
from typing import Optional

import pandas as pd


class LimpiadorVentas:
    """
    Limpia el archivo de análisis de ventas y lo cruza con la tabla maestra.

    Esta clase tiene estado interno, por eso se siente más parecida a Java:
        - Guarda rutas como atributos.
        - Guarda DataFrames intermedios como atributos.
        - Tiene métodos pequeños con una sola responsabilidad.
        - Tiene un método ejecutar() que orquesta todo el proceso.
    """

    COLUMNAS_BASE = ["PRODUCTO", "CODIGO_PRODUCTO"]

    COLUMNAS_FINALES = [
        "CODIGO_PRODUCTO",
        "NOMBRE_PRODUCTO",
        "CLASIFICACION I",
        "CLASIFICACION II",
        "CLASIFICACION III",
        "CLASIFICACION IV",
        "PERIODO",
        "CANTIDAD_FACTURADA",
    ]

    def __init__(
        self,
        ruta_ventas: str | Path,
        carpeta_salida: str | Path,
        ruta_maestro: str | Path | None = None,
        hoja_ventas: str = "Análisis de ventas",
        hoja_maestro: str = "Productos",
        fila_encabezado_ventas: int = 2,
        filas_iniciales_a_eliminar: int = 2,
        nombre_archivo_salida: str = "Ventas_Limpias_PowerBI.xlsx",
    ) -> None:
        """
        Inicializa el limpiador de ventas.

        Parámetros:
            ruta_ventas: Ruta del Excel original de ventas.
            carpeta_salida: Carpeta donde se guardará el Excel limpio.
            ruta_maestro: Ruta opcional de la tabla maestra de productos.
            hoja_ventas: Nombre de la hoja de ventas.
            hoja_maestro: Nombre de la hoja de la tabla maestra.
            fila_encabezado_ventas: Fila usada como encabezado real. En pandas es base cero.
            filas_iniciales_a_eliminar: Filas basura después del encabezado que se deben eliminar.
            nombre_archivo_salida: Nombre del archivo final generado.
        """
        self.ruta_ventas = Path(ruta_ventas)
        self.ruta_maestro = Path(ruta_maestro) if ruta_maestro else None
        self.carpeta_salida = Path(carpeta_salida)
        self.hoja_ventas = hoja_ventas
        self.hoja_maestro = hoja_maestro
        self.fila_encabezado_ventas = fila_encabezado_ventas
        self.filas_iniciales_a_eliminar = filas_iniciales_a_eliminar
        self.nombre_archivo_salida = nombre_archivo_salida

        self.df_ventas_original: Optional[pd.DataFrame] = None
        self.df_ventas_largo: Optional[pd.DataFrame] = None
        self.df_maestro: Optional[pd.DataFrame] = None
        self.df_final: Optional[pd.DataFrame] = None
        self.productos_sin_codigo: pd.DataFrame = pd.DataFrame(columns=["PRODUCTO"])
        self.productos_no_clasificados: pd.DataFrame = pd.DataFrame(columns=["CODIGO_PRODUCTO", "PRODUCTO"])
        self.df_resumen: Optional[pd.DataFrame] = None

    def validar_archivos_entrada(self) -> None:
        """Valida que los archivos necesarios existan antes de leerlos."""
        if not self.ruta_ventas.exists():
            raise FileNotFoundError(
                f"No se encontró el archivo de ventas: {self.ruta_ventas}\n"
                "Revisa la ruta. Si el notebook está en Source y los datos en Data, usa ../Data/nombre_archivo.xlsx"
            )

        if self.ruta_maestro is not None and not self.ruta_maestro.exists():
            raise FileNotFoundError(
                f"No se encontró la tabla maestra: {self.ruta_maestro}\n"
                "Ejecuta primero el extractor de tabla maestra o corrige la ruta."
            )

    @staticmethod
    def normalizar_columnas(df: pd.DataFrame) -> pd.DataFrame:
        """Normaliza encabezados: mayúsculas, sin espacios extremos y espacios internos simples."""
        df = df.copy()
        df.columns = (
            df.columns.astype(str)
            .str.strip()
            .str.upper()
            .str.replace(r"\s+", " ", regex=True)
        )
        return df

    @staticmethod
    def normalizar_codigo(serie: pd.Series) -> pd.Series:
        """Convierte códigos de producto a texto limpio sin espacios."""
        return (
            serie.astype("string")
            .str.strip()
            .str.replace(r"\s+", "", regex=True)
        )

    @staticmethod
    def limpiar_texto(serie: pd.Series) -> pd.Series:
        """Limpia espacios en textos y conserva una sola separación entre palabras."""
        return (
            serie.astype("string")
            .str.strip()
            .str.replace(r"\s+", " ", regex=True)
        )

    def cargar_ventas(self) -> pd.DataFrame:
        """Carga el Excel de ventas y elimina las filas/columnas no útiles del reporte."""
        self.validar_archivos_entrada()

        self.df_ventas_original = pd.read_excel(
            self.ruta_ventas,
            sheet_name=self.hoja_ventas,
            header=self.fila_encabezado_ventas,
            index_col=0,
        )

        # Eliminamos filas iniciales que no son productos reales.
        if self.filas_iniciales_a_eliminar > 0:
            self.df_ventas_original = self.df_ventas_original.iloc[self.filas_iniciales_a_eliminar:].copy()

        # En el reporte actual, la última columna corresponde a total o columna no necesaria.
        if len(self.df_ventas_original.columns) > 0:
            self.df_ventas_original = self.df_ventas_original.drop(columns=[self.df_ventas_original.columns[-1]])

        # Recuperamos la columna de producto que venía como índice.
        self.df_ventas_original = self.df_ventas_original.reset_index().rename(columns={"index": "PRODUCTO"})
        self.df_ventas_original = self.normalizar_columnas(self.df_ventas_original)

        return self.df_ventas_original

    def extraer_codigos_producto(self) -> pd.DataFrame:
        """Extrae CODIGO_PRODUCTO desde la columna PRODUCTO."""
        if self.df_ventas_original is None:
            raise ValueError("Primero debes ejecutar cargar_ventas().")

        if "PRODUCTO" not in self.df_ventas_original.columns:
            raise ValueError("No se encontró la columna PRODUCTO en el archivo de ventas.")

        self.df_ventas_original["PRODUCTO"] = self.limpiar_texto(self.df_ventas_original["PRODUCTO"])

        self.df_ventas_original["CODIGO_PRODUCTO"] = self.normalizar_codigo(
            self.df_ventas_original["PRODUCTO"].str.extract(r"\[(.*?)\]", expand=False)
        )

        # Guardamos aparte los productos que no traen código para que el usuario los revise.
        self.productos_sin_codigo = (
            self.df_ventas_original[self.df_ventas_original["CODIGO_PRODUCTO"].isna()][["PRODUCTO"]]
            .drop_duplicates()
            .reset_index(drop=True)
        )

        # Para el análisis final dejamos solo productos con código.
        self.df_ventas_original = self.df_ventas_original[
            self.df_ventas_original["CODIGO_PRODUCTO"].notna()
        ].copy()

        return self.df_ventas_original

    def convertir_a_formato_largo(self) -> pd.DataFrame:
        """
        Convierte la tabla de formato ancho a formato largo.

        Formato ancho:
            Producto | Enero | Febrero | Marzo

        Formato largo para Power BI:
            Producto | Periodo | Cantidad
        """
        if self.df_ventas_original is None:
            raise ValueError("Primero debes ejecutar extraer_codigos_producto().")

        columnas_meses = [col for col in self.df_ventas_original.columns if col not in self.COLUMNAS_BASE]
        if not columnas_meses:
            raise ValueError("No se encontraron columnas de meses para convertir a formato largo.")

        self.df_ventas_original[columnas_meses] = self.df_ventas_original[columnas_meses].apply(
            pd.to_numeric,
            errors="coerce",
        )

        self.df_ventas_largo = self.df_ventas_original.melt(
            id_vars=["CODIGO_PRODUCTO", "PRODUCTO"],
            value_vars=columnas_meses,
            var_name="PERIODO",
            value_name="CANTIDAD_FACTURADA",
        )

        return self.df_ventas_largo

    def limpiar_valores_facturados(self) -> pd.DataFrame:
        """Elimina ventas nulas, ventas en cero y ventas negativas."""
        if self.df_ventas_largo is None:
            raise ValueError("Primero debes ejecutar convertir_a_formato_largo().")

        self.df_ventas_largo = self.df_ventas_largo.dropna(subset=["CANTIDAD_FACTURADA"])
        self.df_ventas_largo = self.df_ventas_largo[self.df_ventas_largo["CANTIDAD_FACTURADA"] > 0].copy()

        self.df_ventas_largo["CODIGO_PRODUCTO"] = self.normalizar_codigo(self.df_ventas_largo["CODIGO_PRODUCTO"])
        self.df_ventas_largo["PERIODO"] = self.limpiar_texto(self.df_ventas_largo["PERIODO"]).str.upper()
        self.df_ventas_largo = self.df_ventas_largo.reset_index(drop=True)

        return self.df_ventas_largo

    def cargar_tabla_maestra(self) -> Optional[pd.DataFrame]:
        """Carga la tabla maestra si el usuario entregó una ruta."""
        if self.ruta_maestro is None:
            self.df_maestro = None
            return None

        self.df_maestro = pd.read_excel(self.ruta_maestro, sheet_name=self.hoja_maestro)
        self.df_maestro = self.normalizar_columnas(self.df_maestro)

        if "CODIGO_PRODUCTO" not in self.df_maestro.columns:
            raise ValueError("La tabla maestra debe tener la columna CODIGO_PRODUCTO.")

        self.df_maestro["CODIGO_PRODUCTO"] = self.normalizar_codigo(self.df_maestro["CODIGO_PRODUCTO"])
        self.df_maestro = self.df_maestro.drop_duplicates(subset=["CODIGO_PRODUCTO"])

        return self.df_maestro

    def cruzar_con_tabla_maestra(self) -> pd.DataFrame:
        """Cruza ventas limpias con tabla maestra para agregar clasificaciones."""
        if self.df_ventas_largo is None:
            raise ValueError("Primero debes ejecutar limpiar_valores_facturados().")

        if self.df_maestro is None:
            # Si no hay maestro, igual generamos archivo, pero queda sin clasificar.
            self.df_final = self.df_ventas_largo.copy()
            self.df_final["NOMBRE_PRODUCTO"] = self.df_final["PRODUCTO"].str.replace(
                r"^\[[^\]]+\]\s*", "", regex=True
            )
            for columna in ["CLASIFICACION I", "CLASIFICACION II", "CLASIFICACION III", "CLASIFICACION IV"]:
                self.df_final[columna] = "SIN CLASIFICAR"
            return self.df_final

        self.df_final = self.df_ventas_largo.merge(
            self.df_maestro,
            on="CODIGO_PRODUCTO",
            how="left",
        )

        # Guardamos productos que están en ventas pero no aparecen en la tabla maestra.
        self.productos_no_clasificados = (
            self.df_final[self.df_final["NOMBRE_PRODUCTO"].isna()][["CODIGO_PRODUCTO", "PRODUCTO"]]
            .drop_duplicates()
            .sort_values("CODIGO_PRODUCTO")
            .reset_index(drop=True)
        )

        # Para Power BI dejamos solo registros con clasificación válida.
        self.df_final = self.df_final[self.df_final["NOMBRE_PRODUCTO"].notna()].copy()
        return self.df_final

    def ordenar_columnas_finales(self) -> pd.DataFrame:
        """Ordena columnas finales para que el archivo sea cómodo para Power BI."""
        if self.df_final is None:
            raise ValueError("Primero debes ejecutar cruzar_con_tabla_maestra().")

        columnas_existentes = [col for col in self.COLUMNAS_FINALES if col in self.df_final.columns]
        columnas_extra = [col for col in self.df_final.columns if col not in columnas_existentes and col != "PRODUCTO"]

        self.df_final = self.df_final[columnas_existentes + columnas_extra].copy()

        columnas_orden = [col for col in ["PERIODO", "CLASIFICACION I", "NOMBRE_PRODUCTO"] if col in self.df_final.columns]
        if columnas_orden:
            self.df_final = self.df_final.sort_values(columnas_orden).reset_index(drop=True)

        return self.df_final

    def generar_resumen(self) -> pd.DataFrame:
        """Genera una hoja de resumen para validar rápidamente la limpieza."""
        if self.df_final is None:
            raise ValueError("Primero debes ejecutar ordenar_columnas_finales().")

        total_facturado = self.df_final["CANTIDAD_FACTURADA"].sum() if "CANTIDAD_FACTURADA" in self.df_final.columns else 0

        self.df_resumen = pd.DataFrame(
            {
                "METRICA": [
                    "Registros finales",
                    "Cantidad total facturada",
                    "Productos sin código",
                    "Productos no clasificados",
                    "Periodos procesados",
                    "Productos clasificados únicos",
                ],
                "VALOR": [
                    len(self.df_final),
                    total_facturado,
                    len(self.productos_sin_codigo),
                    len(self.productos_no_clasificados),
                    self.df_final["PERIODO"].nunique() if "PERIODO" in self.df_final.columns else 0,
                    self.df_final["CODIGO_PRODUCTO"].nunique() if "CODIGO_PRODUCTO" in self.df_final.columns else 0,
                ],
            }
        )

        return self.df_resumen

    def exportar(self) -> Path:
        """Exporta el resultado final a Excel con hojas de control."""
        if self.df_final is None or self.df_resumen is None:
            raise ValueError("Primero debes ejecutar generar_resumen().")

        self.carpeta_salida.mkdir(parents=True, exist_ok=True)
        archivo_salida = self.carpeta_salida / self.nombre_archivo_salida

        with pd.ExcelWriter(archivo_salida, engine="openpyxl") as writer:
            self.df_final.to_excel(writer, sheet_name="Ventas_Limpias", index=False)
            self.df_resumen.to_excel(writer, sheet_name="Resumen_Limpieza", index=False)
            self.productos_sin_codigo.to_excel(writer, sheet_name="Productos_Sin_Codigo", index=False)
            self.productos_no_clasificados.to_excel(writer, sheet_name="Productos_No_Clasificados", index=False)

        return archivo_salida

    def ejecutar(self) -> Path:
        """
        Ejecuta el flujo completo de limpieza de ventas.

        Retorna:
            Path: ruta del archivo Excel final generado.
        """
        self.cargar_ventas()
        self.extraer_codigos_producto()
        self.convertir_a_formato_largo()
        self.limpiar_valores_facturados()
        self.cargar_tabla_maestra()
        self.cruzar_con_tabla_maestra()
        self.ordenar_columnas_finales()
        self.generar_resumen()
        return self.exportar()


def main() -> None:
    """Permite ejecutar este archivo directamente desde consola."""
    parser = argparse.ArgumentParser(description="Limpieza orientada a objetos de análisis de ventas.")
    parser.add_argument("--ventas", required=True, help="Ruta del Excel de ventas.")
    parser.add_argument("--maestro", default=None, help="Ruta opcional de la tabla maestra.")
    parser.add_argument("--salida", default="../Resultado", help="Carpeta de salida.")
    parser.add_argument("--hoja-ventas", default="Análisis de ventas", help="Nombre de la hoja de ventas.")
    parser.add_argument("--hoja-maestro", default="Productos", help="Nombre de la hoja de tabla maestra.")

    args = parser.parse_args()

    limpiador = LimpiadorVentas(
        ruta_ventas=args.ventas,
        ruta_maestro=args.maestro,
        carpeta_salida=args.salida,
        hoja_ventas=args.hoja_ventas,
        hoja_maestro=args.hoja_maestro,
    )

    archivo_generado = limpiador.ejecutar()
    print(f"Archivo de ventas limpio generado correctamente: {archivo_generado}")


if __name__ == "__main__":
    main()
