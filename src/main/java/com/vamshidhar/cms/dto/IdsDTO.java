package com.vamshidhar.cms.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdsDTO {
    @NotEmpty(message = "ids cannot be empty")
    private Set<Long> ids;
}
