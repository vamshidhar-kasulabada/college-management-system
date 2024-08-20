# College Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Contributing](#contributing)
- [Contact](#contact)

## Introduction

The College Management System is a Spring Boot application designed to manage various aspects of a college, including students, professors, and subjects. The system provides RESTful APIs to perform CRUD operations and manage relationships between these entities efficiently.

## Features

- **Student Management:** Add, update, delete, and view student information.
- **Professor Management:** Manage professor data, including assigned subjects.
- **Subject Management:** Manage subject details and their associations with professors and students.
- **DTOs for Data Handling:** Uses Data Transfer Objects (DTOs) to manage and transfer data between layers.
- **Lombok Integration:** Simplifies the creation of getter, setter, and other utility methods.
- **Validation:** Ensures data integrity and consistency with built-in validation mechanisms.
- **Swagger Documentation:** Auto-generated API documentation for easy testing and integration.

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for building the backend.
- **Spring Data JPA**: For ORM and database interactions.
- **Lombok**: To reduce boilerplate code.
- **ModelMapper**: For entity-to-DTO mapping.
- **H2 Database**: In-memory database for development and testing.
- **Swagger**: For API documentation.
- **Maven**: Build and dependency management tool.

## Setup Instructions

### Prerequisites

- Java 8 or higher
- An IDE (e.g., IntelliJ IDEA, Eclipse, or VSCode with Spring Boot support)

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/vamshidhar-kasulabada/college-management-system.git
   cd college-management-system
   ```

2. **Build the Project:**
   ```bash
   ./mvnw clean install
   ```

3. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the Application:**
   - The application will be accessible at `http://localhost:8080`.
   - Swagger UI can be accessed at `http://localhost:8080/swagger-ui.html`.

## Usage

### API Endpoints

- **Students:**
  - `GET /students`: Retrieve all students.
  - `POST /students`: Add a new student.
  - `PUT /students/{id}`: Update a student's details.
  - `DELETE /students/{id}`: Remove a student.

- **Professors:**
  - `GET /professors`: Retrieve all professors.
  - `POST /professors`: Add a new professor.
  - `PUT /professors/{id}`: Update a professor's details.
  - `DELETE /professors/{id}`: Remove a professor.

- **Subjects:**
  - `GET /subjects`: Retrieve all subjects.
  - `POST /subjects`: Add a new subject.
  - `PUT /subjects/{id}`: Update a subject's details.
  - `DELETE /subjects/{id}`: Remove a subject.

For more detailed API documentation, please refer to the [Swagger UI](http://localhost:8080/swagger-ui.html).

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for review.


## Contact

**Vamshidhar Kasulabada**

- GitHub: [vamshidhar-kasulabada](https://github.com/vamshidhar-kasulabada)
- Email: kasulavamshidhar8@gmail.com
