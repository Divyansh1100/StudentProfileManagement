# Student Profile and Skill Development Management System

Spring Boot REST API using JdbcTemplate and MySQL. Java 17+, Maven, XAMPP/MySQL.

## Setup
1. Start Apache (optional) and MySQL in XAMPP.
2. Open phpMyAdmin and import `database.sql` (or run it in SQL tab).
3. Check `src/main/resources/application.properties`; set your MySQL username/password.
4. Import this folder into Eclipse: File > Import > Existing Maven Projects.
5. Run `StudentManagementApplication.java` as Spring Boot App.

## Endpoints (base: http://localhost:8080)
- POST `/students` — add student
- GET `/students` — list all
- GET `/students/{id}` — get by ID
- GET `/students/search/email?email=rahul@gmail.com`
- GET `/students/search/course?course=BTech%20CSE`
- PUT `/students/{id}` — update; send all student fields in JSON
- DELETE `/students/{id}` — delete
- GET `/students/merit` — descending average score
- GET `/students/same-skills` — students grouped by skill
- GET `/students/programming-improvement` — programming below 50 and maths >=75

## Example POST/PUT JSON
```json
{
  "studentId": 101,
  "studentName": "Rahul Sharma",
  "email": "rahul@gmail.com",
  "courseName": "BTech CSE",
  "programmingScore": 90,
  "mathematicsScore": 85,
  "technicalSkills": ["Java", "Python", "SQL"]
}
```

This is a reconstructed source package based on the implementation details discussed in the chat. Compare it with your working Eclipse project before submission; your local screenshots and test evidence must come from your own run.
