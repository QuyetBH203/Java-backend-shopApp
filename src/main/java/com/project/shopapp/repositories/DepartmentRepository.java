package com.project.shopapp.repositories;

import com.project.shopapp.models.Department;
import org.springframework.data.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
    boolean existsByDepartmentCode(String departmentCode);


    @Query("SELECT d FROM Department d WHERE " +
            "(:departmentCode IS NULL OR :departmentCode = '' OR d.departmentCode LIKE %:departmentCode%) " +
            "AND (:name IS NULL OR :name = '' OR d.name LIKE %:name%) " +
            "AND (:address IS NULL OR :address = '' OR d.address LIKE %:address%) " +
            "AND (:updatedAt IS NULL OR d.updatedAt = :updatedAt) " +
            "AND (:createdAt IS NULL OR d.createdAt = :createdAt)")
    Page<Department> searchDepartments(
            @Param("departmentCode") String departmentCode,
            @Param("name") String name,
            @Param("address") String address,
            @Param("updatedAt") LocalDateTime updatedAt,
            @Param("createdAt") LocalDateTime createdAt,
            Pageable pageable
    );
}
