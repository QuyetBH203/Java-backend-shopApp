package com.project.shopapp.services.department;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;
import com.project.shopapp.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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


}
