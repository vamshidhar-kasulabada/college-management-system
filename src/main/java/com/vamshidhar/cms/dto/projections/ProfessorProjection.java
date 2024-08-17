package com.vamshidhar.cms.dto.projections;

import java.util.Set;



public interface ProfessorProjection {
    Long getId();
    String getName();

    Set<ProfessorStudentProjection> getMentees();

    Set<ProfessorStudentProjection> getStudents();

    Set<ProfessorSubjectProjection> getSubjects();

    interface ProfessorStudentProjection {
        Long getId();
        String getName();
    }

    interface ProfessorSubjectProjection{
        Long getId();
        String getTitle();
    }
}
