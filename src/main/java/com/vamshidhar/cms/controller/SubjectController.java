package com.vamshidhar.cms.controller;

import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.dto.projections.SubjectProjection;
import com.vamshidhar.cms.service.SubjectService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    private SubjectDTO createSubject(@RequestBody @Valid SubjectDTO subject) {
        return subjectService.createSubject(subject);
    }

    @PostMapping("/addAll")
    public Set<SubjectProjection> createSubjects(@RequestBody @Valid Set<SubjectDTO> subjects) {
        return subjectService.createSubjects(subjects);
    }

    @GetMapping("/{id}")
    private SubjectProjection getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping
    private Set<SubjectProjection> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping("/search")
    public Set<SubjectProjection> getSubjectsByTitle(@RequestParam(required = true) String title) {
        return subjectService.getSubjectsByTitle(title);
    }

    @PutMapping("/{id}")
    public SubjectProjection updateSubject(@PathVariable Long id, @RequestBody @Valid SubjectDTO sub) {
        return subjectService.updateSubject(id, sub);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }
}
