package com.vamshidhar.cms.service;

import com.vamshidhar.cms.advices.ApiError;
import com.vamshidhar.cms.advices.ApiResponse;
import com.vamshidhar.cms.dto.IdsDTO;
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

    public Set<StudentProjection> searchStudentByName(String name) {
        return studentRepository.findByNameContains(name);
    }

    // sortBy exists and valid, direction can be null or valid
    public Set<StudentProjection> getStudents(String sortBy, String direction) {
        if (direction == null || direction.equals("asc")) {
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
                .orElseThrow(() -> new ResourceNotFoundException(id, "Student"));
    }

    public StudentProjection updateStudent(Long id, StudentDTO stud){
        StudentEntity student =
                studentRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id, "Student"));

        StudentEntity studentToSave = modelMapper.map(stud, StudentEntity.class);
        studentToSave.setId(id);
        studentToSave.setMentor(student.getMentor());
        studentToSave.setProfessors(student.getProfessors());
        studentToSave.setSubjects(student.getSubjects());

        studentRepository.save(studentToSave);
        return this.getStudentById(id);
    }

    public StudentProjection patchProfessor(Long studentId, Long profId, String option) {
        StudentEntity student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));
        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Professor"));
        if (option.equals("add")) {
            student.getProfessors().add(professor);
        } else {
            student.getProfessors().remove(professor);
        }
        studentRepository.save(student);
        return this.getStudentById(studentId);
    }

    public ApiResponse<StudentProjection> patchProfessors(Long studentId, IdsDTO profIds) {
        StudentEntity student =
               studentRepository 
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));

        Set<ProfessorEntity> professors =
                profIds.getIds().stream()
                        .map(id -> professorRepository.findById(id).orElse(null))
                        .filter(e -> e != null)
                        .collect(Collectors.toSet());

        Set<Long> invalidIds =
                profIds.getIds().stream()
                        .filter(id -> !professorRepository.existsById(id))
                        .collect(Collectors.toSet());

        student.getProfessors().addAll(professors);
        studentRepository.save(student);

        ApiResponse<StudentProjection> response = new ApiResponse<>(this.getStudentById(studentId));
        if (invalidIds.size() > 0) {
            ApiError error =
                    ApiError.builder()
                            .message("Found Invalid Ids")
                            .subErrors(invalidIds.stream().map(String::valueOf).toList())
                            .build();
            response.setError(error);
        }

        return response;

    }

    public StudentProjection pathSubject(Long studentId, Long subId, String option) {
        StudentEntity student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));
        SubjectEntity subject =
                subjectRepository
                        .findById(subId)
                        .orElseThrow(() -> new ResourceNotFoundException(subId, "Subject"));
        if (option.equals("add")) {
            student.getSubjects().add(subject);
        } else {
            student.getSubjects().remove(subject);
        }
        studentRepository.save(student);
        return this.getStudentById(studentId);
    }

    public ApiResponse<StudentProjection> patchSubjects(Long studentId, IdsDTO subIds) {
        StudentEntity student =
               studentRepository 
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));

        Set<SubjectEntity> subjects =
                subIds.getIds().stream()
                        .map(id -> subjectRepository.findById(id).orElse(null))
                        .filter(e -> e != null)
                        .collect(Collectors.toSet());

        Set<Long> invalidIds =
                subIds.getIds().stream()
                        .filter(id -> !subjectRepository.existsById(id))
                        .collect(Collectors.toSet());

        student.getSubjects().addAll(subjects);
        studentRepository.save(student);

        ApiResponse<StudentProjection> response = new ApiResponse<>(this.getStudentById(studentId));
        if (invalidIds.size() > 0) {
            ApiError error =
                    ApiError.builder()
                            .message("Found Invalid Ids")
                            .subErrors(invalidIds.stream().map(String::valueOf).toList())
                            .build();
            response.setError(error);
        }

        return response;

    }

    public StudentProjection patchMentor(Long studentId, Long profId) {
        StudentEntity student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));
        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(profId, "Professor"));
        student.setMentor(professor);
        studentRepository.save(student);
        return this.getStudentById(studentId);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
