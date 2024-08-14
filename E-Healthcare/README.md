# eHealthCareSystem

eHealthCareSystem is a Spring Boot-based application designed to manage healthcare data efficiently. The application provides a robust API for interacting with the healthcare data, tested using Postman and documented with Swagger. It includes comprehensive unit tests with JUnit and Mockito and uses MySQL as the database.

## Features

- CRUD operations for healthcare entities
- RESTful API endpoints
- API documentation with Swagger
- Unit and integration testing with JUnit and Mockito
- MySQL database integration

## Technologies Used

- Java
- Spring Boot
- MySQL
- Swagger
- JUnit
- Mockito
- Postman

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- MySQL

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/ehealthcaresystem.git
    cd ehealthcaresystem
    ```

2. **Set up the MySQL database:**

    Create a database named `ehealthcaresystem` and update the `application.properties` file with your MySQL credentials.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ehealthcaresystem
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

3. **Build the project:**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

### API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html` after starting the application.

### Testing

#### Running Unit Tests

The application includes unit tests using JUnit and Mockito. You can run the tests using Maven:

```bash
mvn test


