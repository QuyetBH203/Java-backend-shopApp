package com.project.shopapp.responses;

import com.project.shopapp.models.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class DepartmentListResponse {
    private List<Department> departments;
    private int totalPages;
}
