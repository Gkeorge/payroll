# Payroll (Employee Management)
Mini project for managing employees using Spring Boot and Mongo DB. Demonstrate features such as database migration using *Mongock* and integration testing using *test containers*.

# Requirements
* Apache Maven 3.6
* Java 11
* Docker

# Build
Build the application using:
> mvn clean package

# Run
* Run the included docker-compose file with: 
 >_docker-compose up_
* Run application by Spring boot maven plugin 
 >_mvn spring-boot:run_

# Demo
The employee management REST API provides access to CRUD endpoints
to manage employees

>The Employee Object
> 
>**Attributes**
>>id - String - Identity for employee
> 
>>name - String - Name of employee; required
> 
>>emailAddress - String - Email address of employee; required
> 
>>role - String - Role of employee; required



>**Endpoints**
>>GET /api/v1/employees - Get employees
> 
>>GET /api/v1/employees/:id - Get employee by ID
> 
>>POST /api/v1/employees - Create employee
> 
>>PUT /api/v1/employees/:id - Update an existing employee
> 
>>DELETE /api/v1/employees/:id - Delete an employee
