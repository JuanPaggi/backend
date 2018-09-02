# Readme plz

Busco y ofrezco backend

## Layers

El backend está compuesto por 3 capas, Web, Service, Model.
El mismo publicará una api GraphQL que será consumida por el frontend, y manejará las sesiones usando JWT.

### Capa de presentación

La capa web se encargará de recibir las solicitudes externas y obtener la información necesaria, luego enviará una respuesta a quien realizó la petición.
Además se encargará de procesar la lógica de negocio.

### Capa de servicios

La capa de servicios se encargará de recepcionar las solicitudes de datos de la capa de presentación, y transferirlas a la capa de acceso a los datos, cuando la misma responda, la capa de servicios empaquetará la información en data transfer objects y los devolverá a la capa de presentación.

### Capa de acceso a datos

La capa de acceso a datos recibirá las solicitudes de datos y conectará con el motor de datos (MySQL), luego devolverá los resultados en crudo.

### Flujo

En resumen, funcionaría de la siguiente forma, el frontend hace una petición a la capa de presentación, acto seguido la misma realiza una petición por datos a la capa de servicios, la capa de servicios ejecuta todas las llamadas a la capa de acceso a datos que necesite y compone un DTO de salida que lo devuelve a la capa de presentación, la misma realiza cualquier operación explícita del modelo de negocios y devuelve la información ordenada o procesada.

## Dependencias y tecnologías

El backend se trabaja con java, y utiliza Maven Repository para gestionar las dependencias.