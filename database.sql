CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;
CREATE TABLE IF NOT EXISTS students (
  student_id INT PRIMARY KEY,
  student_name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  course_name VARCHAR(100),
  programming_score DOUBLE NOT NULL,
  mathematics_score DOUBLE NOT NULL
);
CREATE TABLE IF NOT EXISTS student_skills (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  skill VARCHAR(100) NOT NULL,
  CONSTRAINT fk_student_skills FOREIGN KEY (student_id)
    REFERENCES students(student_id) ON DELETE CASCADE
);
