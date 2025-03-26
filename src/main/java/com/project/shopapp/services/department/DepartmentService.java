package com.project.shopapp.services.department;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;
import com.project.shopapp.repositories.DepartmentRepository;
import com.project.shopapp.responses.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;
    @Override
    @Transactional
    public Department createDepartment(DepartmentDTO department) {
        Department newDepartment = Department
                .builder()
                .name(department.getName())
                .departmentCode(department.getDepartmentCode())
                .address(department.getAddress())
                .build();
        return departmentRepository.save(newDepartment);
    }

    @Override
    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Override
    public Department updateDepartment(long departmentId, DepartmentDTO department) {
        Department existingDepartment = getDepartmentById(departmentId);
        if(existingDepartment == null) {
            throw new RuntimeException("Department not found");
        }
        if(department.getName() != null && !department.getName().isEmpty()) {
            existingDepartment.setName(department.getName());
        }
        if(department.getDepartmentCode() != null && !department.getDepartmentCode().isEmpty()) {
            existingDepartment.setDepartmentCode(department.getDepartmentCode());
        }
        if(department.getAddress() != null && !department.getAddress().isEmpty()) {
            existingDepartment.setAddress(department.getAddress());
        }
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDepartment(long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public boolean existsByDepartmentCode(String departmentCode) {
        return departmentRepository.existsByDepartmentCode(departmentCode);
    }

    @Override
    public Page<Department> searchDepartments(String departmentCode, String name, String address,
                                                      LocalDateTime updatedAt,
                                                      LocalDateTime createdAt, PageRequest pageRequest) {
        return departmentRepository.searchDepartments(departmentCode, name, address, updatedAt, createdAt, pageRequest);

    }


}
