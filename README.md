# Employee Management System - Backend

Spring Boot REST API with JWT Authentication and MySQL database.

## Tech Stack
- Java 17
- Spring Boot 3.5
- MySQL
- JWT Authentication
- Maven

## Setup

### Prerequisites
- Java 17+
- MySQL
- Maven

### Database Setup
```sql
CREATE DATABASE employee_db;
```

### Configuration
Copy `application.properties.example` to `application.properties` and update:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_secret_key
```

### Run
```bash
mvn spring-boot:run
```

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login — returns JWT token |

### Employees
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get by ID |
| GET | /api/employees/by-email/{email} | Get by email |
| POST | /api/employees | Add employee |
| POST | /api/employees/bulk | Bulk import |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |

### Users
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/users | Get all users |
| PUT | /api/users/{id}/toggle-active | Activate/Deactivate |
| PUT | /api/users/{id}/make-employee | Convert to employee |

## Bulk Import Rules

### Excel Format
Headers must be Name | Email | phone | Department :

### Rules
- ✅ Email is required
- ✅ Duplicate emails are skipped
- ✅ Same row duplicates removed
- ✅ ID column ignored on import
- ❌ Missing email → error message

## Author
Pratiksha Agrawal