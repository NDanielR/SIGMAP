"""
Archivo: main_timaran.py

Responsabilidad:
    Ejecutar todo el proceso del Proyecto Timaran desde un solo archivo.

Este archivo es parecido al método main() en Java:
    - Importa las clases principales.
    - Define las rutas.
    - Crea los objetos.
    - Ejecuta el flujo completo.

Uso desde consola estando dentro de la carpeta Source:
    python main_timaran.py

Estructura esperada:
    Proyecto Timaran/
    ├── Data/
    │   ├── Informacion sin limpiar.xlsx
    │   └── Tabla Analisis de ventas.xlsx
    ├── Resultado/
    └── Source/
        ├── main_timaran.py
        ├── extraccion_tabla_maestra.py
        └── limpieza_ventas_oop.py
"""

from pathlib import Path

from extraccion_tabla_maestra import ExtractorTablaMaestra
from limpieza_ventas_oop import LimpiadorVentas


def main() -> None:
    """Punto de entrada principal del proyecto."""

    # Como este archivo vive en Source, subimos un nivel con .. y entramos a Data.
    data_dir = Path("../Data")
    resultado_dir = Path("../Resultado")

    archivo_info_sin_limpiar = data_dir / "Informacion sin limpiar.xlsx"
    archivo_ventas = data_dir / "Tabla Analisis de ventas.xlsx"
    archivo_tabla_maestra = data_dir / "Tabla_Maestra_Productos.xlsx"

    print("Iniciando proceso completo de limpieza...\n")

    # 1. Extraer tabla maestra.
    extractor = ExtractorTablaMaestra(
        ruta_archivo=archivo_info_sin_limpiar,
        ruta_salida=archivo_tabla_maestra,
    )
    ruta_maestra = extractor.ejecutar()
    print(f"Tabla maestra generada: {ruta_maestra}")

    # 2. Limpiar ventas usando tabla maestra.
    limpiador = LimpiadorVentas(
        ruta_ventas=archivo_ventas,
        ruta_maestro=ruta_maestra,
        carpeta_salida=resultado_dir,
    )
    archivo_final = limpiador.ejecutar()
    print(f"Archivo final generado: {archivo_final}\n")

    print("Resumen de limpieza:")
    print(limpiador.df_resumen.to_string(index=False))


if __name__ == "__main__":
    main()
