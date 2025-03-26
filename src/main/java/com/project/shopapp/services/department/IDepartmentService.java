package com.project.shopapp.services.department;

import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;

public interface IDepartmentService {
    Department createDepartment(DepartmentDTO department);
    Department getDepartmentById(long id);

    Department updateDepartment(long departmentId, DepartmentDTO department);
    void deleteDepartment(long id);
    boolean existsByName(String name);
    boolean existsByDepartmentCode(String departmentCode);
}
