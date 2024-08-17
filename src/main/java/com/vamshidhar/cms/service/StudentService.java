package com.vamshidhar.cms.service;

import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.dto.projections.*;
import com.vamshidhar.cms.entities.ProfessorEntity;
import com.vamshidhar.cms.entities.StudentEntity;
import com.vamshidhar.cms.entities.SubjectEntity;
import com.vamshidhar.cms.exceptions.ResourceNotFoundException;
import com.vamshidhar.cms.repository.ProfessorRepository;
import com.vamshidhar.cms.repository.StudentRepository;
import com.vamshidhar.cms.repository.SubjectRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    public StudentProjection createStudent(StudentDTO student) {
        StudentEntity savedStudent =
                studentRepository.save(modelMapper.map(student, StudentEntity.class));

        return getStudentById(savedStudent.getId());
    }

    public Set<StudentProjection> createStudents(Set<StudentDTO> students) {
        Set<StudentEntity> studentEntities =
                students.stream()
                        .map(e -> modelMapper.map(e, StudentEntity.class))
                        .collect(Collectors.toSet());
        List<StudentEntity> savedStudents = studentRepository.saveAll(studentEntities);
        Set<Long> studentIds =
                savedStudents.stream().map(e -> e.getId()).collect(Collectors.toSet());
        return getStudents(studentIds);
    }

    public Set<StudentProjection> getStudentsByName(String name) {
        return studentRepository.findByNameContains(name);
    }

    // sortBy exists and valid, direction can be null or valid
    public Set<StudentProjection> getStudents(String sortBy, String direction) {
        if (direction == null || direction == "asc") {
            sortBy = sortBy.equals("roll_no") ? "rollNo" : sortBy;
            return studentRepository.findAllStudentProjections(Sort.by(Order.asc(sortBy)));
        }
        return studentRepository.findAllStudentProjections(Sort.by(Order.desc(sortBy)));
    }

    public Set<StudentProjection> getStudents() {
        return studentRepository.findAllStudentProjections();
    }

    public Set<StudentProjection> getStudents(Set<Long> studentIds) {
        return studentRepository.findByIdIn(studentIds);
    }

    public StudentProjection getStudentById(Long id) {
        return studentRepository
                .findByIdProjection(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found id: " + id));
    }

    public StudentDTO patchProfessor(Long studentId, Long profId, String option) {
        StudentEntity student = studentRepository.findById(studentId).get();
        ProfessorEntity professor = professorRepository.findById(profId).get();
        if (option.equals("add")) {
            student.getProfessors().add(professor);
        } else {
            student.getProfessors().remove(professor);
        }
        studentRepository.save(student);
        professorRepository.save(professor);
        return modelMapper.map(student, StudentDTO.class);
    }

    public StudentDTO pathSubject(Long studentId, Long subId, String option) {
        StudentEntity student = studentRepository.findById(studentId).get();
        SubjectEntity subject = subjectRepository.findById(subId).get();
        if (option.equals("add")) {
            student.getSubjects().add(subject);
        } else {
            student.getSubjects().remove(subject);
        }
        studentRepository.save(student);
        return modelMapper.map(student, StudentDTO.class);
    }

    public StudentDTO patchMentor(Long studentId, Long profId) {
        StudentEntity student = studentRepository.findById(studentId).get();
        ProfessorEntity professor = professorRepository.findById(profId).get();
        student.setMentor(professor);
        studentRepository.save(student);
        return modelMapper.map(student, StudentDTO.class);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
