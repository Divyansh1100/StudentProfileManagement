package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) { this.repository = repository; }

    public Student addStudent(Student s) {
        if (repository.getStudentById(s.getStudentId()) != null) throw new IllegalArgumentException("Duplicate student ID");
        validate(s); repository.addStudent(s); return repository.getStudentById(s.getStudentId());
    }
    private void validate(Student s) {
        if (s.getProgrammingScore() < 0 || s.getProgrammingScore() > 100 || s.getMathematicsScore() < 0 || s.getMathematicsScore() > 100)
            throw new IllegalArgumentException("Scores must be between 0 and 100");
    }
    public List<Student> getAllStudents() { return repository.getAllStudents(); }
    public Student getStudentById(int id) { return repository.getStudentById(id); }
    public List<Student> searchByEmail(String email) { return repository.getByEmail(email); }
    public List<Student> searchByCourse(String course) { return repository.getByCourse(course); }
    public Student updateStudent(int id, Student s) {
        if (repository.getStudentById(id) == null) return null;
        s.setStudentId(id); validate(s); repository.updateStudent(s); return repository.getStudentById(id);
    }
    public boolean deleteStudent(int id) {
        if (repository.getStudentById(id) == null) return false;
        repository.deleteStudent(id); return true;
    }
    public List<Student> getMeritList() {
        return getAllStudents().stream().sorted(Comparator.comparingDouble(Student::calculateAverage).reversed()).collect(Collectors.toList());
    }
    public Map<String, List<Student>> getStudentsWithSameSkills() {
        Map<String, List<Student>> result = new HashMap<>();
        for (Student s : getAllStudents()) for (String skill : s.getTechnicalSkills())
            result.computeIfAbsent(skill, k -> new ArrayList<>()).add(s);
        return result;
    }
    public List<Student> getProgrammingImprovementStudents() {
        return getAllStudents().stream().filter(s -> s.getProgrammingScore() < 50 && s.getMathematicsScore() >= 75).collect(Collectors.toList());
    }
}
