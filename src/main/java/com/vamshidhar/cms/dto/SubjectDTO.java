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
public class SubjectDTO {
    Long id;
    @NotBlank(message = "title must not be blank")
    String title;
}
