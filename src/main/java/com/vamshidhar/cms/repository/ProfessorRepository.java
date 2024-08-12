package com.vamshidhar.cms.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vamshidhar.cms.entities.ProfessorEntity;
import com.vamshidhar.cms.entities.StudentEntity;
import com.vamshidhar.cms.entities.SubjectEntity;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long>{

    @Query("SELECT p.students FROM ProfessorEntity p WHERE p.id = :professorId")
    Set<StudentEntity> findStudentsByProfessorId(@Param("professorId") Long professorId);


    @Query("SELECT p.subjects FROM ProfessorEntity p WHERE p.id = :professorId")
    Set<SubjectEntity> findSubjectsByProfessorId(@Param("professorId") Long professorId);


}
    
