package com.vamshidhar.cms.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vamshidhar.cms.dto.projections.SubjectProjection;
import com.vamshidhar.cms.entities.SubjectEntity;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{

    @Query("SELECT s from SubjectEntity s order by id")
    public Set<SubjectProjection> getSubjects();

    public Set<SubjectProjection> findByIdIn(Set<Long> ids);

    //@Query("SELECT s from SubjectEntity s where title like :title order by id")
    //public Set<SubjectProjection> getByName(String name);
    
    public Set<SubjectProjection> findByTitleContains(String title);


    @Query("SELECT s from SubjectEntity s where s.id=:id")
    public SubjectProjection getSubjectById(Long id);
}
