"""
Módulo: extraccion_tabla_maestra.py

Responsabilidad:
    Extraer la tabla maestra de productos desde el archivo original sin limpiar.

Idea principal:
    Este archivo contiene una clase llamada ExtractorTablaMaestra.
    La clase recibe la ruta del archivo Excel original y genera un archivo Excel
    limpio con el catálogo de productos y sus clasificaciones.

Uso desde notebook:
    from extraccion_tabla_maestra import ExtractorTablaMaestra

    extractor = ExtractorTablaMaestra(
        ruta_archivo="../Data/Informacion sin limpiar.xlsx",
        ruta_salida="../Data/Tabla_Maestra_Productos.xlsx"
    )

    ruta_maestra = extractor.ejecutar()

Uso desde consola:
    python extraccion_tabla_maestra.py --archivo "../Data/Informacion sin limpiar.xlsx" --salida "../Data/Tabla_Maestra_Productos.xlsx"
"""

from __future__ import annotations

import argparse
import re
from pathlib import Path
from typing import Optional

import pandas as pd


class ExtractorTablaMaestra:
    """
    Extrae, limpia y exporta la tabla maestra de productos.

    Esta clase está pensada para que el proceso sea fácil de entender desde POO:
        1. Se crea el objeto con las rutas de entrada y salida.
        2. El objeto guarda internamente los DataFrames como atributos.
        3. El método ejecutar() coordina todo el flujo.

    Atributos principales:
        ruta_archivo: Ruta del Excel original sin limpiar.
        ruta_salida: Ruta donde se guardará la tabla maestra.
        hoja: Nombre de la hoja que se va a leer.
        fila_encabezado: Fila donde están los encabezados reales del Excel.
        df_original: DataFrame leído directamente desde Excel.
        df_productos: DataFrame en proceso de limpieza.
        df_maestro: DataFrame final de tabla maestra.
    """

    COLUMNAS_FINALES = [
        "CODIGO_PRODUCTO",
        "NOMBRE_PRODUCTO",
        "CLASIFICACION I",
        "CLASIFICACION II",
        "CLASIFICACION III",
        "CLASIFICACION IV",
    ]

    COLUMNAS_CLASIFICACION = [
        "CLASIFICACION I",
        "CLASIFICACION II",
        "CLASIFICACION III",
        "CLASIFICACION IV",
    ]

    def __init__(
        self,
        ruta_archivo: str | Path,
        ruta_salida: str | Path,
        hoja: str = "Análisis de ventas",
        fila_encabezado: int = 3,
    ) -> None:
        """
        Inicializa el extractor con las rutas de trabajo.

        Parámetros:
            ruta_archivo: Ruta del archivo Excel de entrada.
            ruta_salida: Ruta completa del archivo Excel de salida.
            hoja: Nombre de la hoja que contiene la información.
            fila_encabezado: Fila usada por pandas como encabezado. En pandas es base cero.
        """
        self.ruta_archivo = Path(ruta_archivo)
        self.ruta_salida = Path(ruta_salida)
        self.hoja = hoja
        self.fila_encabezado = fila_encabezado

        self.df_original: Optional[pd.DataFrame] = None
        self.df_productos: Optional[pd.DataFrame] = None
        self.df_maestro: Optional[pd.DataFrame] = None

    def validar_archivo_entrada(self) -> None:
        """Valida que el archivo de entrada exista antes de intentar leerlo."""
        if not self.ruta_archivo.exists():
            raise FileNotFoundError(
                f"No se encontró el archivo de entrada: {self.ruta_archivo}\n"
                "Revisa la ruta. Si el notebook está en Source y los datos en Data, usa ../Data/nombre_archivo.xlsx"
            )

    def cargar_datos(self) -> pd.DataFrame:
        """Carga el Excel original y lo guarda en self.df_original."""
        self.validar_archivo_entrada()

        self.df_original = pd.read_excel(
            self.ruta_archivo,
            sheet_name=self.hoja,
            header=self.fila_encabezado,
            index_col=0,
        )

        # Convertimos el índice en columna para recuperar la columna PRODUCTO.
        self.df_original = self.df_original.reset_index()
        return self.df_original

    def seleccionar_columnas_producto(self) -> pd.DataFrame:
        """
        Selecciona las primeras columnas donde vienen producto y clasificaciones.

        En tu archivo actual, la tabla maestra sale de las primeras 5 columnas:
            PRODUCTO + CLASIFICACION I + II + III + IV
        """
        if self.df_original is None:
            raise ValueError("Primero debes ejecutar cargar_datos().")

        self.df_productos = self.df_original.iloc[:, 0:5].copy()
        return self.df_productos

    def normalizar_encabezados(self) -> pd.DataFrame:
        """Convierte los encabezados a mayúsculas y limpia espacios."""
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar seleccionar_columnas_producto().")

        self.df_productos.columns = (
            self.df_productos.columns.astype(str)
            .str.strip()
            .str.upper()
            .str.replace(r"\s+", " ", regex=True)
        )
        return self.df_productos

    def normalizar_textos(self) -> pd.DataFrame:
        """Convierte textos a mayúscula y elimina espacios repetidos."""
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar normalizar_encabezados().")

        columnas_texto = [col for col in self.df_productos.columns if self.df_productos[col].dtype == "object"]

        for columna in columnas_texto:
            self.df_productos[columna] = (
                self.df_productos[columna]
                .astype("string")
                .str.upper()
                .str.strip()
                .str.replace(r"\s+", " ", regex=True)
            )

        return self.df_productos

    def separar_codigo_nombre_producto(self) -> pd.DataFrame:
        """
        Separa la columna PRODUCTO en CODIGO_PRODUCTO y NOMBRE_PRODUCTO.

        Ejemplo:
            [2542] LLANTA 12R22.5

        Resultado:
            CODIGO_PRODUCTO = 2542
            NOMBRE_PRODUCTO = LLANTA 12R22.5
        """
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar normalizar_textos().")

        if "PRODUCTO" not in self.df_productos.columns:
            raise ValueError("No se encontró la columna PRODUCTO en la tabla base.")

        self.df_productos["CODIGO_PRODUCTO"] = (
            self.df_productos["PRODUCTO"]
            .astype("string")
            .str.extract(r"\[(.*?)\]", expand=False)
            .astype("string")
            .str.strip()
            .str.replace(r"\s+", "", regex=True)
        )

        self.df_productos["NOMBRE_PRODUCTO"] = (
            self.df_productos["PRODUCTO"]
            .astype("string")
            .str.replace(r"\[.*?\]\s*", "", regex=True)
            .str.strip()
            .str.replace(r"\s+", " ", regex=True)
        )

        self.df_productos = self.df_productos.drop(columns=["PRODUCTO"])
        return self.df_productos

    def rellenar_clasificaciones_vacias(self) -> pd.DataFrame:
        """Rellena las clasificaciones vacías con NO APLICA."""
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar separar_codigo_nombre_producto().")

        columnas_rellenar = [
            "CLASIFICACION II",
            "CLASIFICACION III",
            "CLASIFICACION IV",
        ]

        for columna in columnas_rellenar:
            if columna in self.df_productos.columns:
                self.df_productos[columna] = (
                    self.df_productos[columna]
                    .replace(r"^\s*$", pd.NA, regex=True)
                    .fillna("NO APLICA")
                )

        return self.df_productos

    def eliminar_registros_invalidos(self) -> pd.DataFrame:
        """Elimina registros sin código de producto o sin nombre de producto."""
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar rellenar_clasificaciones_vacias().")

        self.df_productos = self.df_productos[
            self.df_productos["CODIGO_PRODUCTO"].notna()
            & self.df_productos["NOMBRE_PRODUCTO"].notna()
            & (self.df_productos["CODIGO_PRODUCTO"].astype("string").str.strip() != "")
            & (self.df_productos["NOMBRE_PRODUCTO"].astype("string").str.strip() != "")
        ].copy()

        return self.df_productos

    def ordenar_columnas(self) -> pd.DataFrame:
        """Ordena las columnas finales de la tabla maestra."""
        if self.df_productos is None:
            raise ValueError("Primero debes ejecutar eliminar_registros_invalidos().")

        columnas_existentes = [col for col in self.COLUMNAS_FINALES if col in self.df_productos.columns]
        self.df_maestro = self.df_productos[columnas_existentes].copy()

        # Quitamos duplicados por código para evitar problemas al cruzar con ventas.
        self.df_maestro = (
            self.df_maestro
            .drop_duplicates(subset=["CODIGO_PRODUCTO"])
            .sort_values("CODIGO_PRODUCTO")
            .reset_index(drop=True)
        )

        return self.df_maestro

    def exportar(self) -> Path:
        """Exporta la tabla maestra a Excel."""
        if self.df_maestro is None:
            raise ValueError("Primero debes ejecutar ordenar_columnas().")

        self.ruta_salida.parent.mkdir(parents=True, exist_ok=True)

        with pd.ExcelWriter(self.ruta_salida, engine="openpyxl") as writer:
            self.df_maestro.to_excel(writer, sheet_name="Productos", index=False)

        return self.ruta_salida

    def ejecutar(self) -> Path:
        """
        Ejecuta el proceso completo de extracción.

        Retorna:
            Path: ruta del archivo generado.
        """
        self.cargar_datos()
        self.seleccionar_columnas_producto()
        self.normalizar_encabezados()
        self.normalizar_textos()
        self.separar_codigo_nombre_producto()
        self.rellenar_clasificaciones_vacias()
        self.eliminar_registros_invalidos()
        self.ordenar_columnas()
        return self.exportar()

    def resumen(self) -> pd.DataFrame:
        """Devuelve un resumen simple para validar el resultado desde el notebook."""
        if self.df_maestro is None:
            raise ValueError("Primero debes ejecutar ejecutar().")

        return pd.DataFrame(
            {
                "METRICA": [
                    "Productos en tabla maestra",
                    "Códigos únicos",
                    "Clasificación I únicas",
                    "Clasificación II únicas",
                    "Clasificación III únicas",
                    "Clasificación IV únicas",
                ],
                "VALOR": [
                    len(self.df_maestro),
                    self.df_maestro["CODIGO_PRODUCTO"].nunique(),
                    self.df_maestro["CLASIFICACION I"].nunique() if "CLASIFICACION I" in self.df_maestro.columns else 0,
                    self.df_maestro["CLASIFICACION II"].nunique() if "CLASIFICACION II" in self.df_maestro.columns else 0,
                    self.df_maestro["CLASIFICACION III"].nunique() if "CLASIFICACION III" in self.df_maestro.columns else 0,
                    self.df_maestro["CLASIFICACION IV"].nunique() if "CLASIFICACION IV" in self.df_maestro.columns else 0,
                ],
            }
        )


def main() -> None:
    """Permite ejecutar este archivo directamente desde consola."""
    parser = argparse.ArgumentParser(description="Extrae la tabla maestra de productos.")
    parser.add_argument("--archivo", required=True, help="Ruta del archivo Excel sin limpiar.")
    parser.add_argument("--salida", required=True, help="Ruta del Excel de tabla maestra a generar.")
    parser.add_argument("--hoja", default="Análisis de ventas", help="Nombre de la hoja origen.")
    parser.add_argument("--fila-encabezado", type=int, default=3, help="Fila de encabezado para pandas. Base cero.")

    args = parser.parse_args()

    extractor = ExtractorTablaMaestra(
        ruta_archivo=args.archivo,
        ruta_salida=args.salida,
        hoja=args.hoja,
        fila_encabezado=args.fila_encabezado,
    )

    archivo_generado = extractor.ejecutar()
    print(f"Tabla maestra generada correctamente: {archivo_generado}")


if __name__ == "__main__":
    main()
