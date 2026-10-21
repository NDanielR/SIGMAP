# Swagger: administración de grúas

Con la API iniciada y PostgreSQL disponible:

- Interfaz: http://localhost:8080/swagger-ui.html
- Documento OpenAPI JSON: http://localhost:8080/v3/api-docs

Abre el grupo **Grúas**, selecciona una operación y pulsa **Try it out**.
Las operaciones se ejecutan contra la base de datos de la aplicación.

| Método | Ruta | Resultado correcto |
| --- | --- | --- |
| GET | `/api/cranes` | 200, lista de grúas |
| GET | `/api/cranes/id/{id}` | 200, una grúa |
| GET | `/api/cranes/name/{name}` | 200, una grúa por coincidencia parcial |
| POST | `/api/cranes` | 201, grúa creada |
| PUT | `/api/cranes/{id}` | 200, grúa actualizada |
| DELETE | `/api/cranes/{id}` | 204, sin cuerpo |

Ejemplo para POST:

```json
{
  "name": "QC01",
  "ip": "192.168.0.1",
  "rack": 0,
  "slot": 2,
  "type": "QC"
}
```

Para PUT, usa el mismo ejemplo sin `type`. El ID se envía en la URL.
El tipo se conserva y el ID del cuerpo se ignora.

Las consultas por ID y nombre devuelven 404 cuando no encuentran una grúa.
POST devuelve 409 por nombre o IP existentes; PUT devuelve 409 por nombre
asignado a otra grúa. DELETE también devuelve 204 si el ID no existe.

La búsqueda por nombre no distingue mayúsculas y admite coincidencias parciales,
pero su resultado es único: varias coincidencias pueden producir un error.
Los DTO actuales no tienen restricciones de validación de campos; `@Valid`
por sí solo no valida campos vacíos ni el formato de la IP.

Las anotaciones documentan el comportamiento actual; no añaden validaciones.
