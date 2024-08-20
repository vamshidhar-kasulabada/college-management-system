package com.vamshidhar.cms.service;

import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.dto.projections.SubjectProjection;
import com.vamshidhar.cms.entities.SubjectEntity;
import com.vamshidhar.cms.exceptions.ResourceNotFoundException;
import com.vamshidhar.cms.repository.SubjectRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private final ModelMapper modelMapper;
    private final SubjectRepository subjectRepository;

    public SubjectDTO createSubject(SubjectDTO subject) {
        SubjectEntity subEntity =
                subjectRepository.save(modelMapper.map(subject, SubjectEntity.class));
        return modelMapper.map(subEntity, SubjectDTO.class);
    }

    public Set<SubjectProjection> createSubjects(Set<SubjectDTO> subjects) {
        Set<SubjectEntity> subjectEntities =
                subjects.stream()
                        .map(e -> modelMapper.map(e, SubjectEntity.class))
                        .collect(Collectors.toSet());
        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjectEntities);
        Set<Long> subjectIds =
                savedSubjects.stream().map(e -> e.getId()).collect(Collectors.toSet());
        return getSubjects(subjectIds);
    }

    public SubjectProjection getSubjectById(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException(id, "Subject");
        }
        return subjectRepository.getSubjectById(id);
    }

    public Set<SubjectProjection> getSubjects() {
        return subjectRepository.getSubjects();
    }

    public SubjectProjection updateSubject(Long id, SubjectDTO sub){
        SubjectEntity subject = subjectRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException(id, "Subject"));
        subject.setTitle(sub.getTitle());
        subjectRepository.save(subject);
        return this.getSubjectById(id);
    }

    public void deleteSubject(Long id){
        subjectRepository.deleteById(id);
    }
    public Set<SubjectProjection> getSubjectsByTitle(String title){
        return subjectRepository.findByTitleContains(title);
    }

    private Set<SubjectProjection> getSubjects(Set<Long> subjectIds) {
        return subjectRepository.findByIdIn(subjectIds);
    }

}
