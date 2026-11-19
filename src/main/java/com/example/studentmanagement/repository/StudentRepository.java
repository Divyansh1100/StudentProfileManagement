package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbc;
    public StudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Student> mapper = (rs, row) -> new Student(
            rs.getInt("student_id"), rs.getString("student_name"), rs.getString("email"),
            rs.getString("course_name"), rs.getDouble("programming_score"),
            rs.getDouble("mathematics_score"), getSkills(rs.getInt("student_id")));

    public void addStudent(Student s) {
        jdbc.update("INSERT INTO students(student_id,student_name,email,course_name,programming_score,mathematics_score) VALUES(?,?,?,?,?,?)",
                s.getStudentId(), s.getStudentName(), s.getEmail(), s.getCourseName(), s.getProgrammingScore(), s.getMathematicsScore());
        saveSkills(s.getStudentId(), s.getTechnicalSkills());
    }
    public List<Student> getAllStudents() { return jdbc.query("SELECT * FROM students", mapper); }
    public Student getStudentById(int id) {
        List<Student> list = jdbc.query("SELECT * FROM students WHERE student_id=?", mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }
    public List<Student> getByEmail(String email) { return jdbc.query("SELECT * FROM students WHERE email=?", mapper, email); }
    public List<Student> getByCourse(String course) { return jdbc.query("SELECT * FROM students WHERE course_name=?", mapper, course); }
    public void updateStudent(Student s) {
        jdbc.update("UPDATE students SET student_name=?,email=?,course_name=?,programming_score=?,mathematics_score=? WHERE student_id=?",
                s.getStudentName(), s.getEmail(), s.getCourseName(), s.getProgrammingScore(), s.getMathematicsScore(), s.getStudentId());
        jdbc.update("DELETE FROM student_skills WHERE student_id=?", s.getStudentId());
        saveSkills(s.getStudentId(), s.getTechnicalSkills());
    }
    public void deleteStudent(int id) { jdbc.update("DELETE FROM students WHERE student_id=?", id); }
    private void saveSkills(int id, List<String> skills) {
        if (skills != null) for (String skill : skills)
            jdbc.update("INSERT INTO student_skills(student_id,skill) VALUES(?,?)", id, skill);
    }
    private List<String> getSkills(int id) {
        return jdbc.query("SELECT skill FROM student_skills WHERE student_id=?", (rs, row) -> rs.getString("skill"), id);
    }
}
