# ✅ Enterprise Asset Monitoring System (Spring Boot Backend)

A secure and scalable backend system designed for managing factory assets, tracking sensor data, scheduling maintenance, and logging uptime/downtime with role-based access for Operators and Managers.

---

## 🎯 Objective

To build an efficient and modular enterprise-grade asset monitoring system to:

- ✅ Register and manage factory assets  
- ✅ Fetch and store simulated sensor data  
- ✅ Log uptime, downtime, and maintenance activities  
- ✅ Support scheduled maintenance operations  
- ✅ Provide role-based access (Operators & Managers)



## 📁 Core Modules

### ✅ 1. User & Role Management
- Register/Login users  
- Role-based access control (Manager / Operator)

### ✅ 2. Asset Management
- CRUD operations for assets  
- Assign assets to specific users  

### ✅ 3. Sensor Data Ingestion
- REST endpoint for simulated sensor data  
- Store data (temperature, pressure, timestamp)  

### ✅ 4. Uptime & Maintenance Logs
- Schedule and log maintenance activities  
- Record asset uptime/downtime  
- Mark assets UP/DOWN based on sensor thresholds  



## ⚙ Tech Stack

| Layer         | Technology                        |
|---------------|-----------------------------------|
| Language       | Java                              |
| Framework      | Spring Boot, Spring Security, Spring Data JPA |
| Database       | MySQL                             |
| REST Client    | RestTemplate                      |
| JSON Mapper    | Jackson                           |
| Build Tool     | Maven / Gradle                    |
| Testing        | JUnit                             |
| API Docs       | Swagger UI (springdoc-openapi)    |
| Dev Tools      | Lombok, Spring DevTools           |



## 🧠 Business Logic Flow

### ✔ Sensor Threshold Check
- Receive temperature/pressure via `/api/sensors/send-data`
- Compare values to asset thresholds
- If threshold exceeded:
  - Log status as `DOWN`
  - Schedule maintenance
  - Alert via console logs or DB entry

### ✔ Uptime and Maintenance
- Track when an asset was last UP/DOWN
- Record maintenance completed date
- Allow operators to manually schedule or complete maintenance



## 🧪 Testing Plan

| Type          | Tool           | Focus Areas                                 |
|---------------|----------------|---------------------------------------------|
| Unit Testing  | JUnit, Mockito | Services, Controllers                       |
| Spring Boot   | Built-in       | REST APIs, Security                         |
| Integration   | Postman        | End-to-end flow for all modules             |



## 📤 Sample API Endpoints

### 🔐 AuthController
| Method | Endpoint             | Description         |
|--------|----------------------|---------------------|
| POST   | /api/auth/register   | Register user       |
| POST   | /api/auth/login      | Login               |

### 👤 UserController
| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| GET    | /api/users            | Get all users        |
| PUT    | /api/users/{id}/role  | Update user role     |

### 🏭 AssetController
| Method | Endpoint              | Description             |
|--------|-----------------------|-------------------------|
| POST   | /api/assets           | Add new asset           |
| GET    | /api/assets           | Get all assets          |
| GET    | /api/assets/{id}      | Get asset by ID         |
| PUT    | /api/assets/{id}      | Update asset            |
| DELETE | /api/assets/{id}      | Delete asset            |

### 🌡 SensorDataController
| Method | Endpoint                       | Description                       |
|--------|--------------------------------|-----------------------------------|
| POST   | /api/sensors/send-data         | Push simulated sensor data        |
| GET    | /api/sensors/asset/{id}        | Get sensor data for an asset      |

### 🛠 MaintenanceController
| Method | Endpoint                       | Description                          |
|--------|--------------------------------|--------------------------------------|
| POST   | /api/maintenance               | Add maintenance log                  |
| GET    | /api/maintenance/asset/{id}    | Get maintenance logs for asset       |

### 📈 UptimeLogController
| Method | Endpoint                    | Description                       |
|--------|-----------------------------|-----------------------------------|
| GET    | /api/uptime/asset/{id}      | Get uptime logs for asset         |



## 🗂 Project Structure

```bash
com.example.EnterpriseAssetMonitoringSystem
├── config
│ ├── SecurityConfig.java
│ └── SwaggerConfig.java
│
├── controller
│ ├── AssetController.java
│ ├── AuthenticationController.java
│ ├── MaintenanceController.java
│ ├── SensorController.java
│ ├── UptimeController.java
│ └── UserController.java
│
├── dto
│ ├── AssetDTO.java
│ ├── LoginDTO.java
│ ├── RegisterDTO.java
│ ├── MaintenanceDTOs.java
│ └── SensorDTO.java
│
├── entity
│ ├── Asset.java
│ ├── MaintenanceLog.java
│ ├── SensorData.java
│ ├── UptimeLog.java
│ ├── User.java
│ └── Role.java
│
├── exception
│ ├── GlobalExceptionHandler.java
│ ├── AssetException.java
│ ├── MaintenanceException.java
│ ├── ObjectNotFoundException.java
│ ├── UnauthorizedException.java
│ └── UserException.java
│
├── repository
│ ├── AssetRepository.java
│ ├── MaintenanceRepository.java
│ ├── SensorRepository.java
│ ├── UptimeLogRepository.java
│ └── UserRepository.java
│
├── service
│ ├── AssetService.java
│ ├── MaintenanceService.java
│ ├── SensorService.java
│ ├── UptimeService.java
│ └── UserService.java
│
└── EnterpriseAssetMonitoringSystemApplication.java
```

## ▶ How to Run the Project

### 🛠 Prerequisites

- Java 17+  
- Maven  
- MySQL  
- Postman / Swagger

### 🚀 Setup Steps

1. **Clone the repo**
   git clone https://github.com/Rakii21/EnterpriseAssetMonitoringSystem
   cd EnterpriseAssetMonitoringSystem
   
2. **Create MySQL DB**
   CREATE DATABASE assetmanagementtest;
   
3. **Update application.properties**
  spring.datasource.url=jdbc:mysql://localhost:3306/assetmanagementtest?useSSL=false
  spring.datasource.username=root
  spring.datasource.password=Password

  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

  springdoc.swagger-ui.path=/swagger-ui/index.html
  
4. **Build and run**
  ./mvnw clean install
  ./mvnw spring-boot:run
   
6. **Access Swagger**
  http://localhost:8080/swagger-ui/index.html
  
## 👩‍💻 Contributors

| Name                   | Role & Contributions                     |
| ---------------------- | ---------------------------------------- |
| *Rakesh E*             | Project Lead, Maintenance Module |
| *Yuvathika B*          | User Module               |
| *Srinithi R*           | Asset Module              |
| *Kaustuk Saraf*        | Sensor Data Module               |
| *Anamika Jain*         | Uptime Log Module              |
