# DAQ API — captura PLC con Moka7

La captura lee variables de bloques DB de un PLC y guarda un historial en PostgreSQL.
Servicios: `PlcClientService` (lectura y conversión), `PlcAcquisitionService` (persistencia)
y `PlcAcquisitionScheduler` (ejecución periódica).

## Configuración

1. Crear la base PostgreSQL y ejecutar `db/001_plc_readings.sql` una sola vez.
   Hibernate valida el esquema; no crea ni modifica tablas de producción.
2. Definir `DB_URL` (por ejemplo `jdbc:postgresql://localhost:5432/daq`),
   `DB_USERNAME` y `DB_PASSWORD` en el entorno.
3. Definir `PLC_HOST`, `PLC_RACK`, `PLC_SLOT` y opcionalmente `PLC_NAME`.
4. Configurar `daq.plc.tags` en `application.properties` según el mapa real del PLC.
   Los ejemplos comentados son ilustrativos, no direcciones verificadas.
5. Definir `PLC_ENABLED=true` y arrancar la aplicación con Java 21 y Maven.

Tipos soportados: BOOL (1 byte y bit 0–7), INT (2 bytes con signo),
DINT (4 bytes con signo), REAL (4 bytes). `offset` se expresa en bytes.
Se almacena el valor como texto junto con su tipo, dirección, nombre del PLC,
nombre de variable y fecha de captura UTC. No se escriben datos al PLC.

`PLC_POLL_INTERVAL_MS` configura la espera entre el final de una captura y la
siguiente (5000 ms por defecto). `PLC_TIMEOUT_MS` configura el timeout de recepción
de Moka7 (2000 ms); el timeout de conexión de la biblioteca es 5000 ms.

Cada ciclo conecta, lee las variables secuencialmente y desconecta en un bloque
finally. No representa una instantánea atómica del PLC. Solo se guarda el lote
si todas las lecturas son válidas; la transacción de base de datos cubre el lote.
Ante fallos se registra el error y se reintenta en el siguiente ciclo, sin guardar
valores ficticios. No hay cola persistente: una caída de la DB pierde esa muestra.
Ejecutar una sola instancia capturadora por PLC para evitar muestras duplicadas.

La configuración actual cubre un PLC y una tabla nueva `plc_readings`. Para usar
tablas existentes hace falta definir su correspondencia con las variables.
La habilitación de acceso y las direcciones deben comprobarse en el PLC real.

## Pruebas

`mvn test`: pruebas de conversión, dirección leída, fallos/reconexión y persistencia
contra H2 usando el mismo SQL de creación. No requieren PLC ni PostgreSQL reales.
