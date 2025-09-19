# customer-data-ms
Custormer data microservice 

### 📝 Brief summary

> customer-data-ms its a single microservice created for retrieve and process data from snowflake instance database,
> created using a nearest hexagonal architecture approach (ports and adapters) due the complexity technical requirements 
> for three differents types of datasources(snowflake, kafka and mongodb).
> The layers are explained below
>
## 📁 Hexagonal architecture
```
/customer-data-ms
│── pom.xml
│── Dockerfile
│── docker-compose.yml
│── .env
│── src
    └── main
        ├── java
        │   └── com.ngo.customers.data
        │       ├── application          # application layer use cases
        │       │   ├── service
        │       │   │   ├── CustomerService.java
        │       │   │   
        │       │   └── port             # interfaces (ports)
        │       │       ├── in
        │       │       │   ├── CustomerUseCase.java
        │       │       │
        │       │       └── out
        │       │           ├── CustomerRepositoryPort.java   # Snowflake
        │       │           
        │       │
        │       ├── domain              # domain layer (entitie + business logic)
        │       │   ├── model
        │       │   │   ├── AddressData.java
        │       │   │   ├── CustomerAddressData.java
        │       │   │   └── CustomerData.java
        │       │   └── exception
        │       │       └── CustomExceptions.java
        │       │
        │       ├── infrastructure      # secondary adapters (Snowflake, Mongo, Kafka)
        │       │   ├── config
        │       │   │   ├── SnowflakeConfig.java
        │       │   │   ├── MongoConfig.java
        │       │   │
        │       │   ├── repository
        │       │   │   ├── SnowflakeCustomerRepository.java  # implements CustomerRepositoryPort
        │       │   │   └── MongoReportRepository.java        # implements ReportRepositoryPort
        │       │   └── messaging
        │       │       └── CustomerKafkaConsumer.java          
		└               └── CustomerKafkaProducer.java         
        │       │
        │       └── adapter             # prymary adapters (REST controllers)
        │       │   └── in
        │       │       └── web
        │       │           ├── CustomerController.java   # GET /customers                   
        │       │
        │       └──CustomerDataMsApplication.java
        └── resources
            ├── application.properties
```

## 🚀 First steps
1. **`Connect snowflake with the microservice instance`**:
    * configure the next in values **in application.properties**
    * **_spring.datasource.url=jdbc:snowflake://your_account.snowflakecomputing.com/?db=SNOWFLAKE_SAMPLE_DATA&schema=TPCDS_SF10TCL&CLIENT_METADATA_REQUEST_USE_CONNECTION_CTX=false&JDBC_QUERY_RESULT_FORMAT=JSON_**
    *  _**spring.datasource.username=your_username**_
    *  _**spring.datasource.password=your_password**_
    * create an .env file in root directory of project with these values
   ```
    Use this  and replace the sample value for your values account if you want to use docker, after that copy and paste in .env file in
    root folder project
    SNOW_FLAKE_ACCOUNT_ID=yourAccount
    SNOW_FLAKE_DB=yourDB
    SNOW_FLAKE_SCHEMA=yourSchema
    SNOW_FLAKE_USERNAME=yourUserName
    SNOW_FLAKE_PASSWORD=yourPassword
   ```

2. **`Connect mongodb using docker-compose and mongo db`**:
    * **_SPRING_DATA_MONGODB_URI_**: mongodb://your_user:your_password@mongo:27017/your_db?authSource=admin
3. **`Configure the next properties for kafka in docker compose file`**:
    * set the next values for docker compose file:
     ```
        kafka:
    image: bitnami/kafka:latest
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_CFG_NODE_ID: 1
      KAFKA_CFG_PROCESS_ROLES: broker,controller
      KAFKA_CFG_CONTROLLER_QUORUM_VOTERS: 1@kafka:9093
      KAFKA_CFG_LISTENERS: PLAINTEXT://:9092,CONTROLLER://:9093
      KAFKA_CFG_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_CFG_CONTROLLER_LISTENER_NAMES: CONTROLLER
      ALLOW_PLAINTEXT_LISTENER: "yes"
    volumes:
      - kafka_data:/bitnami/kafka
   ```
4. **`Execute mvn clean install to build the required jar`**:
5. **`open cmd/bash/terminal and type: docker compose up -d --build`**:
6. **`In your browser or Rest prefered client test the next endpoints`**:


| Method | Endpoint                    | Description                                                                 |
| ------ | --------------------------- | --------------------------------------------------------------------------- |
| `GET`  | `http://localhost:8080/api/customers?page=2&size=20`           | Retrieves a paginated list of customers from Snowflake.                     |
| `GET`  | `/http://localhost:8080/api/customers/fetch/AAAAAAAADCAAAAAA` | Fetches a specific customer by ID from Snowflake and publishes it to Kafka. |
| `GET`  | `http://localhost:8080/api/customers/mongo`      | Returns all customer records stored in MongoDB. (for verification)          |

---
