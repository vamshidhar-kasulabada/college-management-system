package com.vamshidhar.cms.dto.projections;

import java.util.Set;

public interface SubjectProjection {

    Long getId();
    String getTitle();
    Set<SubjectStudentProjection>  getStudents();
    Set<SubjectProfessorProjection>  getProfessors();

    interface SubjectStudentProjection{
        Long getId();
        String getName();
    }

    interface SubjectProfessorProjection{
        Long getId();
        String getName();
    }
}
