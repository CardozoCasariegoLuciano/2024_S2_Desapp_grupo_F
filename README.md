# Crypto Exchange UNQ - DESAPP

### Descripción 
    
Se requiere construir un MVP (Minimum Viable Product) para el uso Peer-to-Peer (p2p) de criptomonedas. 

El objetivo del producto es generar una comunidad de confianza para poder canjear criptomonedas por pesos argentinos. 

El servicio de P2P es muy simple, una persona pone a la venta X cantidad de determinado criptoactivo a determinado precio y otro usuario teniendo en cuenta su reputación anterior (cantidad de operación, y % de operación exitosas) selecciona el mejor vendedor y se realiza la transacciòn. 

La persona envía el dinero al vendedor y avisa por la plataforma, cuando la persona que vendió los cripto activos chequea que recibió el dinero, envía los cripto activos a la dirección correspondiente y da por finalizada la transacción. 

Las operaciones de movimientos de dinero o cripto activos quedan por fuera de la plataforma.

### CI Status

- ### Build:
    ![Build Status](https://github.com/CardozoCasariegoLuciano/2024_S2_Desapp_grupo_F/actions/workflows/maven.yml/badge.svg?branch=master&job=build)

- ### SonarCloud: 
    ![SonarCloud Status](https://github.com/CardozoCasariegoLuciano/2024_S2_Desapp_grupo_F/actions/workflows/maven.yml/badge.svg?branch=master&job=sonarcloud)
    ![Coverage](https://sonarcloud.io/api/project_badges/measure?project=unq.CryptoExchange%3ACryptoExchange&metric=coverage)

- ### Coverage Report
    ![Coverage Report Status](https://github.com/CardozoCasariegoLuciano/2024_S2_Desapp_grupo_F/actions/workflows/maven.yml/badge.svg?branch=master&job=coverage)

### Entrega 1 - 17/09/2024

| Tareas                                                                                                   |  |
|-----------------------------------------------------------------------------------------------------------------|--------|
| *Core*                                                                                                        |        |
| Creación de repositorios GitHub                                                                                 | ✅      |
| Configuración en GitHubActions                                                                                  | ✅      |
| Build corriendo y SUCCESS                                                                                       | ✅      |
| SonarCloud (Registrar el proyecto Backend)                                                                      | ✅      |
| Deploy automático utilizando HEROKU o cualquier servicio similar para deploy Automático                         | ✅      |
| TAG en GitHub y Confeccionar Release Notes de entrega 1 [Ver](https://github.com/CardozoCasariegoLuciano/2024_S2_Desapp_grupo_F/compare/Entrega_1_v1.0...Entrega_2_v1.0) | ✅ |
| Clean Code según la materia (todo en Inglés)                                                                    | ✅      |
| Configuración de [Swagger](https://swagger.io/) en el back-API (v3)                                             | ✅      |
| *Modelo*                                                                                                      |        |
| Implementar modelo completo                                                                                     | ✅      |
| Testing automático unitario según las pautas de la materia                                                      | ✅      |
| *Funcionalidad*                                                                                               |        |
| Proveer servicio de registración de usuario (punto 1)                                                           | ✅      |

### Entrega 2 - 22/10/2024

| Tareas                                                                                                           |  |
|-----------------------------------------------------------------------------------------------------------------|--------|
| *Core*                                                                                                        |        |
| Estado de build en "Verde"                                                                                      | ✅      |
| Utilizar HSQLDB para persistir datos (opción H2)                                                                | ✅      |
| Crear datos de prueba cuando levanta la aplicación                                                              | ✅      |
| Documentación de Endpoints (APIs) con Swagger (v3)                                                              | ✅      |
| TAG en GitHub y Confeccionar Release Notes de entrega 2 [Ver](https://github.com/CardozoCasariegoLuciano/2024_S2_Desapp_grupo_F/releases/tag/Entrega_2_v1.0) | ✅ |
| Implementar JOB de Coverage                                                                                     | ✅      |
| Testing de endpoints usando Postman                                                                             | ✅      |
| *Funcionalidad*                                                                                              |        |
| Listar cotización de criptoactivos                                                                              | ✅      |
| Permitir que un usuario exprese su intención de compra/venta                                                    | ✅      |
| Construir un listado donde se muestran las intenciones activas de compra/venta                                  | ✅      |
| Procesar la transacción informada por un usuario                                                                | ❌      |
| Informar al usuario el volumen operado de criptoactivos entre dos fechas                                        | ✅      |
| Testing integral de 2 controllers (end-to-end)                                                                  | ✅      |


