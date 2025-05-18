# Hacom

## Build
docker-compose build

## Run
docker-compose up

## Test
Creacion de Orden por GRPC: Ejecutar la clase GrcpOrderClient.java
Prueba de Endpoints: Exportar el "Hacom.postman_collection.json" en la carpeta "postman" y probar los endpoints

## Verified
En la url http://localhost:9898/actuator/prometheus
Buscar el contador "orders_created_total"