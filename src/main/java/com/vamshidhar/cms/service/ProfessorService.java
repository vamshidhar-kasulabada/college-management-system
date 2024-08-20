package com.vamshidhar.cms.service;

import com.vamshidhar.cms.advices.ApiError;
import com.vamshidhar.cms.advices.ApiResponse;
import com.vamshidhar.cms.dto.IdsDTO;
import com.vamshidhar.cms.dto.ProfessorDTO;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ModelMapper modelMapper;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public ProfessorProjection createProfessor(ProfessorDTO professor) {
        ProfessorEntity savedProfessor =
                professorRepository.save(modelMapper.map(professor, ProfessorEntity.class));
        return getProfessorById(savedProfessor.getId());
    }

    public Set<ProfessorProjection> createProfessors(Set<ProfessorDTO> professors) {
        Set<ProfessorEntity> profEntities =
                professors.stream()
                        .map(e -> modelMapper.map(e, ProfessorEntity.class))
                        .collect(Collectors.toSet());
        List<ProfessorEntity> savedProfessors = professorRepository.saveAll(profEntities);
        Set<Long> ids = savedProfessors.stream().map(e -> e.getId()).collect(Collectors.toSet());
        return getProfessors(ids);
    }

    public Set<ProfessorProjection> getProfessors() {
        return professorRepository.findAllProjection();
    }

    public Set<ProfessorProjection> getProfessors(Set<Long> ids) {
        return professorRepository.findByIdIn(ids);
    }

    public ProfessorProjection getProfessorById(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException(id, "Professor");
        }
        return professorRepository.findByIdProjection(id);
    }

    public Set<ProfessorProjection> searchProfessorByName(String name) {
        return professorRepository.findByNameContains(name);
    }

    public ProfessorProjection updateProfessor(Long id, ProfessorDTO professor){
        ProfessorEntity prof = professorRepository.findById(id).
            orElseThrow(()-> new ResourceNotFoundException(id, "Professor"));
        prof.setName(professor.getName());
        professorRepository.save(prof);
        return this.getProfessorById(id);
    }


    public ProfessorProjection patchSubject(Long profId, Long subId, String option) {
        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(profId, "Professor"));
        SubjectEntity subject =
                subjectRepository
                        .findById(subId)
                        .orElseThrow(() -> new ResourceNotFoundException(subId, "Subject"));

        if (option.equals("add")) {
            professor.getSubjects().add(subject);
        } else {
            professor.getSubjects().remove(subject);
        }
        ProfessorEntity savedProfessor = professorRepository.save(professor);
        return getProfessorById(savedProfessor.getId());
    }

    public ApiResponse<ProfessorProjection> patchSubjects(Long profId, IdsDTO ids) {
        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(profId, "Professor"));

        Set<SubjectEntity> subjects =
                ids.getIds().stream()
                        .map(id -> subjectRepository.findById(id).orElse(null))
                        .filter(e -> e != null)
                        .collect(Collectors.toSet());

        Set<Long> invalidIds =
                ids.getIds().stream()
                        .filter(id -> !subjectRepository.existsById(id))
                        .collect(Collectors.toSet());
        professor.getSubjects().addAll(subjects);
        professorRepository.save(professor);

        ApiResponse<ProfessorProjection> response = new ApiResponse<>(getProfessorById(profId));
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

    public ProfessorProjection patchStudent(Long profId, Long studentId, String option) {

        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(profId, "Professor"));

        StudentEntity student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException(studentId, "Student"));

        if (option.equals("add")) {
            professor.getStudents().add(student);
            student.getProfessors().add(professor);
        } else {
            professor.getStudents().remove(student);
            student.getProfessors().remove(professor);
        }
        studentRepository.save(student);
        ProfessorEntity savedProfessor = professorRepository.save(professor);
        return getProfessorById(savedProfessor.getId());
    }

    public ApiResponse<ProfessorProjection> patchMentees(Long profId, IdsDTO studentIds) {
        ProfessorEntity professor =
                professorRepository
                        .findById(profId)
                        .orElseThrow(() -> new ResourceNotFoundException(profId, "Professor"));

        Set<StudentEntity> students =
                studentIds.getIds().stream()
                        .map(id -> studentRepository.findById(id).orElse(null))
                        .filter(e -> e != null)
                        .collect(Collectors.toSet());

        Set<Long> invalidIds =
                studentIds.getIds().stream()
                        .filter(id -> !studentRepository.existsById(id))
                        .collect(Collectors.toSet());


        //professor.getMentees().addAll(students);
        //professorRepository.save(professor);
        
        students.stream()
            .forEach(e->{
                e.setMentor(professor);
            });
        studentRepository.saveAll(students);

        ApiResponse<ProfessorProjection> response = new ApiResponse<>(getProfessorById(profId));
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

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

}
