package com.vamshidhar.cms.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDTO {
    private Long id;
    private String name;
    String branch;
    String course;
    Long rollNo;
    private Set<ProfessorDTO> professors;
    private Set<SubjectDTO> subjects;
    private ProfessorDTO mentor;
}
