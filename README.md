# JIS Sequencer

Production parts sequencing management system for Just-In-Sequence (JIS) logistics.

## Overview

JIS Sequencer is a REST API application for managing the sequence of loading automotive parts on a production line. The system allows registering scanned parts, generating loading plans, and confirming part loading in the correct sequence.

### Key Features

- **Part Registration**: Scanning and registering production parts with unique sequence numbers
- **Loading Plan**: Retrieving a list of parts in the correct loading sequence
- **Load Confirmation**: Marking parts as loaded
- **Uniqueness Control**: Protection against duplicate sequence numbers

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
  - Spring Web (REST API)
  - Spring Data JPA (data persistence)
- **H2 Database** (embedded database)
- **Maven** (dependency management and build)

## Requirements

- Java 17 or higher
- Maven 3.6+

## Installation and Running

### 1. Clone the project

```bash
git clone <repository-url>
cd jis-sequencer
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## API Endpoints

### 1. Register Part (Scan)

Registers a new part in the system with a unique sequence number.

**Endpoint:**
```
POST /api/jis/scan
```

**Request Body:**
```json
{
  "sequence": 1,
  "code": "PART-12345",
  "model": "Model S"
}
```

**Response:**
```json
{
  "id": 1,
  "sequenceNumber": 1,
  "partCode": "PART-12345",
  "carModel": "Model S",
  "scannedAt": "2026-02-16T10:30:00",
  "loaded": false
}
```

**Response Codes:**
- `200 OK` - part successfully registered
- `400 Bad Request` - sequence number already exists

### 2. Get Loading Plan

Returns a list of unloaded parts, sorted by sequence number.

**Endpoint:**
```
GET /api/jis/plan
```

**Response:**
```json
[
  {
    "id": 1,
    "sequenceNumber": 1,
    "partCode": "PART-12345",
    "carModel": "Model S",
    "scannedAt": "2026-02-16T10:30:00",
    "loaded": false
  },
  {
    "id": 2,
    "sequenceNumber": 2,
    "partCode": "PART-67890",
    "carModel": "Model X",
    "scannedAt": "2026-02-16T10:31:00",
    "loaded": false
  }
]
```

### 3. Confirm Load

Marks a part as loaded.

**Endpoint:**
```
PUT /api/jis/load/{id}
```

**Path Parameter:**
- `id` - Part ID in the system

**Response:**
- `200 OK` - load confirmed
- `404 Not Found` - part not found

## Usage Examples

### Using cURL

```bash
# Register a part
curl -X POST http://localhost:8080/api/jis/scan \
  -H "Content-Type: application/json" \
  -d '{"sequence": 1, "code": "PART-12345", "model": "Model S"}'

# Get loading plan
curl http://localhost:8080/api/jis/plan

# Confirm load
curl -X PUT http://localhost:8080/api/jis/load/1
```

### Using PowerShell

```powershell
# Register a part
Invoke-RestMethod -Uri "http://localhost:8080/api/jis/scan" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"sequence": 1, "code": "PART-12345", "model": "Model S"}'

# Get loading plan
Invoke-RestMethod -Uri "http://localhost:8080/api/jis/plan" -Method GET

# Confirm load
Invoke-RestMethod -Uri "http://localhost:8080/api/jis/load/1" -Method PUT
```

## Project Structure

```
jis-sequencer/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/logistics/jis/
│       │       ├── JisApplication.java          # Application entry point
│       │       ├── ProductionPart.java          # Part entity
│       │       ├── PartRepository.java          # Repository for database operations
│       │       ├── SequenceService.java         # Business logic
│       │       └── SequenceController.java      # REST controller
│       └── resources/
│           └── application.properties           # Application configuration
├── pom.xml                                      # Maven configuration
└── README.md
```

## Data Model

### ProductionPart

| Field | Type | Description |
|------|-----|----------|
| id | Long | Unique identifier (auto-generated) |
| sequenceNumber | Integer | Sequence number in the order (unique) |
| partCode | String | Part code |
| carModel | String | Car model |
| scannedAt | LocalDateTime | Scanning time |
| loaded | Boolean | Loading status |

## Database

The application uses an embedded H2 database running in in-memory mode. Data is stored in memory and cleared when the application restarts.

For persistent data storage, you can configure a file-based H2 database or use another DBMS (PostgreSQL, MySQL, etc.).

## Configuration

Main settings can be changed in the `src/main/resources/application.properties` file:

```properties
# Server port
server.port=8080

# H2 settings
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:jisdb
spring.datasource.driverClassName=org.h2.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Development

### Build without running tests

```bash
mvn clean package -DskipTests
```

### Run JAR file

```bash
java -jar target/jis-sequencer-0.0.1-SNAPSHOT.jar
```






