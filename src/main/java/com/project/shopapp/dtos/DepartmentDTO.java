package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DepartmentDTO {
    @JsonProperty("department_code")
    @NotBlank(message = "Department code is required")
    @Size(min = 4, message = "Department code must be at least 4 characters")
    private String departmentCode;

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("address")
    @NotBlank(message = "Address is required")
    private String address;
}
