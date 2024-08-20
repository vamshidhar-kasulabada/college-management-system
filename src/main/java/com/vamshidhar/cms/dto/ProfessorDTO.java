package com.vamshidhar.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorDTO {
    Long id;
    @NotBlank(message = "name must not be blank")
    String name;
}

