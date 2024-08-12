package com.vamshidhar.cms.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamshidhar.cms.dto.ProfessorDTO;
import com.vamshidhar.cms.dto.StudentDTO;
import com.vamshidhar.cms.dto.SubjectDTO;
import com.vamshidhar.cms.repository.ProfessorRepository;
import com.vamshidhar.cms.service.ProfessorService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ProfessorDTO createProfessor(@RequestBody ProfessorDTO professor){
        return professorService.createProfessor(professor);
    }

    @GetMapping("/{id}")
    public ProfessorDTO getProfessorById(@PathVariable Long id){
        return professorService.getProfessorById(id);
    }

    @GetMapping
    public Set<ProfessorDTO> getProfessors(){
        return professorService.getProfessors();
    }

    @GetMapping("/{id}/students")
    public Set<StudentDTO> getStudents(@PathVariable Long id){
        return professorService.getStudents(id);
    }

    @GetMapping("/{id}/subjects")
    public Set<SubjectDTO> getSubjects(@PathVariable Long id){
        return professorService.getSubjects(id);
    }


        @PatchMapping("/{profId}/subject/{subId}")
    public ProfessorDTO patchSubject(
            @PathVariable Long profId, @PathVariable Long subId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return professorService.patchSubject(profId, subId, option);
        }
        throw new IllegalArgumentException();
    }


}
