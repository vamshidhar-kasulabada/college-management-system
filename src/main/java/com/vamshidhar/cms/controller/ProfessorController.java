package com.vamshidhar.cms.controller;

import com.vamshidhar.cms.advices.ApiResponse;
import com.vamshidhar.cms.dto.IdsDTO;
import com.vamshidhar.cms.dto.ProfessorDTO;
import com.vamshidhar.cms.dto.projections.*;
import com.vamshidhar.cms.service.ProfessorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ProfessorProjection createProfessor(@RequestBody @Valid ProfessorDTO professor) {
        return professorService.createProfessor(professor);
    }

    @PostMapping("/addAll")
    public Set<ProfessorProjection> createProfessors(@RequestBody @Valid Set<ProfessorDTO> professor) {
        return professorService.createProfessors(professor);
    }

    @GetMapping("/{id}")
    public ProfessorProjection getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id);
    }

    @GetMapping
    public Set<ProfessorProjection> getProfessors() {
        return professorService.getProfessors();
    }

    @GetMapping("/search")
    public Set<ProfessorProjection> searchProfessorsByName(@RequestParam String name) {
        return professorService.searchProfessorByName(name);
    }

    @PutMapping("/{id}")
    public ProfessorProjection updateProfessor(@PathVariable Long id , @RequestBody @Valid ProfessorDTO professor){
        return professorService.updateProfessor(id, professor);
    }

    @PatchMapping("/{profId}/subject/{subId}")
    public ProfessorProjection patchSubject(
            @PathVariable Long profId, @PathVariable Long subId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return professorService.patchSubject(profId, subId, option);
        }
        throw new IllegalArgumentException("Invalid option: " + option + ". choose add or remove.");
    }

    @PatchMapping("/{profId}/subject/addAll")
    public ApiResponse<ProfessorProjection> patchSubjects(
            @PathVariable Long profId, @RequestBody IdsDTO ids) {
        return professorService.patchSubjects(profId, ids);
    }

    @PatchMapping("/{profId}/student/{studentId}")
    public ProfessorProjection patchStudent(
            @PathVariable Long profId, @PathVariable Long studentId, @RequestParam String option) {
        if (option.equals("add") || option.equals("remove")) {
            return professorService.patchStudent(profId, studentId, option);
        }
        throw new IllegalArgumentException("Invalid option: " + option + ". choose add or remove.");
    }

    @PatchMapping("/{profId}/mentees/addAll")
    public ApiResponse<ProfessorProjection> patchMentees(
            @PathVariable Long profId, @RequestBody IdsDTO studentIds) {
        return professorService.patchMentees(profId, studentIds);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
