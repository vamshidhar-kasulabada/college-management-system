
package com.vamshidhar.cms.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.entities.SubjectEntity;
import com.vamshidhar.cms.repository.SubjectRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectService {
    private final ModelMapper modelMapper;
    private final SubjectRepository subjectRepository;


    public SubjectDTO createSubject(SubjectDTO subject){
        SubjectEntity subEntity = subjectRepository.save(modelMapper.map(subject, SubjectEntity.class));
        return modelMapper.map(subEntity, SubjectDTO.class);
    }

    public SubjectDTO getSubjectById(Long id){
        SubjectEntity subject = subjectRepository.findById(id).get();
        return modelMapper.map(subject, SubjectDTO.class);
    }

    public Set<SubjectDTO> getSubjects(){
        List<SubjectEntity> subEntities = subjectRepository.findAll();
        Set<SubjectDTO> subjects = subEntities.stream().map(e->modelMapper.map(e,SubjectDTO.class)).collect(Collectors.toSet());
        return subjects;
    }
}
