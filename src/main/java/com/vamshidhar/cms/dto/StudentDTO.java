package com.vamshidhar.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "branch must not be blank")
    private String branch;
    @NotBlank(message = "course must not be blank")
    private String course;
    @JsonProperty("roll_no")
    @Positive(message = "Roll Number must be positive")
    @NotNull(message = "roll_no is mandatory")
    private Long rollNo;
}
