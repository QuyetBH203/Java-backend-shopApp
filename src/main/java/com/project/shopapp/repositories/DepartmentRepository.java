package com.project.shopapp.repositories;

import com.project.shopapp.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
    boolean existsByDepartmentCode(String departmentCode);
}
