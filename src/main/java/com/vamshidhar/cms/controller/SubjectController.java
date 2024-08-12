package com.vamshidhar.cms.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.service.SubjectService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("subject")
public class SubjectController {
    private final SubjectService subjectService;


    @PostMapping
    private SubjectDTO createSubject(@RequestBody SubjectDTO subject){
        return subjectService.createSubject(subject);
    }

    @GetMapping("/{id}")
    private SubjectDTO getSubjectById(@PathVariable Long id){
        return subjectService.getSubjectById(id);
    }

    @GetMapping
    private Set<SubjectDTO> getSubjects(){
        return subjectService.getSubjects();
    }
}
