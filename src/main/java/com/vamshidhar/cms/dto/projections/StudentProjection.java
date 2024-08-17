package com.vamshidhar.cms.dto.projections;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StudentProjection {

    Long getId();
    String getName();
    String getBranch();
    String getCourse();
    @JsonProperty("roll_no")
    Long getRollNo();
    StudentProfessorProjection getMentor();
    Set<StudentProfessorProjection> getProfessors();
    Set<StudentSubjectProjection> getSubjects();


    interface StudentProfessorProjection{
        Long getId();
        String getName();
    }

    interface StudentSubjectProjection{
        Long getId();
        String getTitle();
    }


}
