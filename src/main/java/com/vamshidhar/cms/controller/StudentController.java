package com.vamshidhar.cms.controller;

import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.service.StudentService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public Set<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @PatchMapping("/{studentId}/professor/{profId}")
    public StudentDTO assignProfessor(
            @PathVariable Long studentId, @PathVariable Long profId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return studentService.patchProfessor(studentId, profId, option);
        }
        throw new IllegalArgumentException();
    }

    @PatchMapping("/{studentId}/subject/{subId}")
    public StudentDTO patchSubject(
            @PathVariable Long studentId, @PathVariable Long subId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return studentService.pathSubject(studentId, subId, option);
        }
        throw new IllegalArgumentException();
    }

    @PatchMapping("/{studentId}/mentor/{profId}")
    public StudentDTO patchMentor(@PathVariable Long studentId, @PathVariable Long profId) {
        return studentService.patchMentor(studentId, profId);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
