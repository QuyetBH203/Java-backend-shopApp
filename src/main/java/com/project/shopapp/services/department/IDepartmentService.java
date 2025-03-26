package com.project.shopapp.services.department;

import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;
import com.project.shopapp.responses.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public interface IDepartmentService {
    Department createDepartment(DepartmentDTO department);
    Department getDepartmentById(long id);

    Department updateDepartment(long departmentId, DepartmentDTO department);
    void deleteDepartment(long id);
    boolean existsByName(String name);
    boolean existsByDepartmentCode(String departmentCode);

    Page<Department> searchDepartments(String departmentCode, String name, String address, LocalDateTime updatedAt, LocalDateTime createdAt, PageRequest pageRequest);
}
