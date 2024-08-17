package com.vamshidhar.cms.controller;

import com.vamshidhar.cms.advices.ApiResponse;
import com.vamshidhar.cms.dto.IdsDTO;
import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.dto.projections.*;
import com.vamshidhar.cms.service.StudentService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentProjection> createStudent(@RequestBody StudentDTO student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public Set<StudentProjection> createStudents(@RequestBody Set<StudentDTO> students) {
        return studentService.createStudents(students);
    }

    @GetMapping("/{id}")
    public StudentProjection getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping()
    public Set<StudentProjection> getStudents(
            @RequestParam(name = "sortby", required = false) String sortBy,
            @RequestParam(required = false) String direction) {

        // If sortBy is null, but direction is provided, throw an exception
        if (sortBy == null && direction != null) {
            throw new IllegalArgumentException(
                    "The 'sortby' parameter is required when 'direction' is provided.");
        }

        // If sortBy is null, return unsorted students
        if (sortBy == null) {
            return studentService.getStudents();
        }

        // Validate sortBy and direction
        List<String> validProperties = Arrays.asList("id", "name", "branch", "course", "roll_no");
        List<String> validDirections = Arrays.asList("asc", "desc");

        if (validProperties.contains(sortBy) == false) {
            throw new IllegalArgumentException("Invalid sortby: " + sortBy);
        }

        if (direction != null && validDirections.contains(direction) == false) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        return studentService.getStudents(sortBy, direction);
    }

    @GetMapping("/search")
    public Set<StudentProjection> searchStudentByName(@RequestParam(required = true) String name) {
        return studentService.searchStudentByName(name);
    }

    @PatchMapping("/{studentId}/professor/{profId}")
    public StudentProjection patchProfessor(
            @PathVariable Long studentId, @PathVariable Long profId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return studentService.patchProfessor(studentId, profId, option);
        }
        throw new IllegalArgumentException("Invalid option: "+ option +". choose add or remove.");
    }

    @PatchMapping("/{studentId}/professor/addAll")
    public ApiResponse<StudentProjection> patchProfessors(
            @PathVariable Long studentId, @RequestBody IdsDTO profIds) {
            return studentService.patchProfessors(studentId, profIds);
    }

    @PatchMapping("/{studentId}/subject/{subId}")
    public StudentProjection patchSubject(
            @PathVariable Long studentId, @PathVariable Long subId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return studentService.pathSubject(studentId, subId, option);
        }
        throw new IllegalArgumentException("Invalid option: "+ option +". choose add or remove.");
    }

    @PatchMapping("/{studentId}/subject/addAll")
    public ApiResponse<StudentProjection> patchSubjects(
            @PathVariable Long studentId, @RequestBody IdsDTO subIds) {
            return studentService.patchSubjects(studentId, subIds);
    }

    @PatchMapping("/{studentId}/mentor/{profId}")
    public StudentProjection patchMentor(@PathVariable Long studentId, @PathVariable Long profId) {
        return studentService.patchMentor(studentId, profId);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
