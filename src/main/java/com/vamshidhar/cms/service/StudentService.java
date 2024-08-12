package com.vamshidhar.cms.service;

import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.entities.ProfessorEntity;
import com.vamshidhar.cms.entities.StudentEntity;
import com.vamshidhar.cms.entities.SubjectEntity;
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
public class StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    public StudentDTO createStudent(StudentDTO student) {
        StudentEntity savedStudent =
                studentRepository.save(modelMapper.map(student, StudentEntity.class));

        return modelMapper.map(savedStudent, StudentDTO.class);

    }

    public StudentDTO getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id).get();
        return modelMapper.map(student, StudentDTO.class);
    }

    public Set<StudentDTO> getStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        Set<StudentDTO> studentsDtos =
                studentEntities.stream()
                        .map(e -> modelMapper.map(e, StudentDTO.class))
                        .collect(Collectors.toSet());
        return studentsDtos;
    }


    public StudentDTO patchProfessor(Long studentId, Long profId,String option) {
        StudentEntity student = studentRepository.findById(studentId).get();
        ProfessorEntity professor = professorRepository.findById(profId).get();
        if(option.equals("add")){
            student.getProfessors().add(professor);
        }else{
            student.getProfessors().remove(professor);
        }
        studentRepository.save(student);
        return modelMapper.map(student,StudentDTO.class);
    }



    public StudentDTO pathSubject(Long studentId, Long subId, String option){
        StudentEntity student = studentRepository.findById(studentId).get();
        SubjectEntity subject = subjectRepository.findById(subId).get();
        if(option.equals("add")){
            student.getSubjects().add(subject);
        }else{
            student.getSubjects().remove(subject);
        }
        studentRepository.save(student);
        return modelMapper.map(student,StudentDTO.class);
    }

    public StudentDTO patchMentor(Long studentId, Long profId){
        StudentEntity student = studentRepository.findById(studentId).get();
        ProfessorEntity professor = professorRepository.findById(profId).get();
        student.setMentor(professor);
        studentRepository.save(student);
        return modelMapper.map(student,StudentDTO.class);
    }


    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }
}
