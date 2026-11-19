package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @PostMapping public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(service.addStudent(student)); }
        catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
    @GetMapping public List<Student> getAllStudents() { return service.getAllStudents(); }
    @GetMapping("/{id}") public ResponseEntity<?> getStudent(@PathVariable int id) {
        Student s = service.getStudentById(id); return s == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(s);
    }
    @GetMapping("/search/email") public List<Student> searchEmail(@RequestParam String email) { return service.searchByEmail(email); }
    @GetMapping("/search/course") public List<Student> searchCourse(@RequestParam String course) { return service.searchByCourse(course); }
    @PutMapping("/{id}") public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student student) {
        try { Student updated = service.updateStudent(id, student); return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated); }
        catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        return service.deleteStudent(id) ? ResponseEntity.ok("Student deleted successfully!") : ResponseEntity.notFound().build();
    }
    @GetMapping("/merit") public List<Student> meritList() { return service.getMeritList(); }
    @GetMapping("/same-skills") public Map<String, List<Student>> sameSkills() { return service.getStudentsWithSameSkills(); }
    @GetMapping("/programming-improvement") public List<Student> programmingImprovement() { return service.getProgrammingImprovementStudents(); }
}
