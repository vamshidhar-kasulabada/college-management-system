package com.vamshidhar.cms.repository;

import com.vamshidhar.cms.dto.projections.*;
import com.vamshidhar.cms.entities.ProfessorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    @Query("SELECT p FROM ProfessorEntity p")
    Set<ProfessorProjection> findAllProjection();

    Set<ProfessorProjection> findByIdIn(Set<Long> ids);

    @Query("SELECT p FROM ProfessorEntity p where p.id=:id")
    ProfessorProjection findByIdProjection(Long id);

    public Set<ProfessorProjection> findByNameContains(String name);
}
