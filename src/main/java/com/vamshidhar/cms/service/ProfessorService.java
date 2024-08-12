package com.vamshidhar.cms.service;

import com.vamshidhar.cms.dto.ProfessorDTO;
import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.entities.ProfessorEntity;
import com.vamshidhar.cms.entities.StudentEntity;
import com.vamshidhar.cms.entities.SubjectEntity;
import com.vamshidhar.cms.repository.ProfessorRepository;
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

    public ProfessorDTO createProfessor(ProfessorDTO professor) {
        ProfessorEntity savedProfessor =
                professorRepository.save(modelMapper.map(professor, ProfessorEntity.class));
        return modelMapper.map(savedProfessor, ProfessorDTO.class);
    }

    public ProfessorDTO getProfessorById(Long id) {
        ProfessorEntity professor = professorRepository.findById(id).get();
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    public Set<ProfessorDTO> getProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();
        Set<ProfessorDTO> professordtos =
                professors.stream()
                        .map(e -> modelMapper.map(e, ProfessorDTO.class))
                        .collect(Collectors.toSet());
        return professordtos;
    }

    public Set<StudentDTO> getStudents(Long id) {
        Set<StudentEntity> students = professorRepository.findStudentsByProfessorId(id);
        Set<StudentDTO> studentDtos =
                students.stream()
                        .map(e -> modelMapper.map(e, StudentDTO.class))
                        .collect(Collectors.toSet());
        return studentDtos;
    }

    public Set<SubjectDTO> getSubjects(Long id) {
        Set<SubjectEntity> subjects = professorRepository.findSubjectsByProfessorId(id);
        Set<SubjectDTO> subjectDtos =
                subjects.stream()
                        .map(e -> modelMapper.map(e, SubjectDTO.class))
                        .collect(Collectors.toSet());
        return subjectDtos;
    }

    public ProfessorDTO patchSubject(Long profId, Long subId, String option){
        ProfessorEntity professor = professorRepository.findById(profId).orElseGet(null);
        SubjectEntity subject = subjectRepository.findById(subId).orElseGet(null);
        if(option.equals("add")){
            professor.getSubjects().add(subject);
        }else{
            professor.getSubjects().remove(subject);
        }

        professorRepository.save(professor);
        return modelMapper.map(professor, ProfessorDTO.class);
    }
}
