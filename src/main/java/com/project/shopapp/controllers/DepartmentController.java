package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;
import com.project.shopapp.responses.DepartmentResponse;
import com.project.shopapp.services.department.IDepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;
    @GetMapping("")
    public ResponseEntity<?> getDepartments() {
        return ResponseEntity.ok().body("Departments");
    }

    @PostMapping("")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentDTO departmentDTO,
            BindingResult result
    ) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            departmentResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(departmentResponse);
        }
        Department department = departmentService.createDepartment(departmentDTO);
        departmentResponse.setDepartment(department);
        return ResponseEntity.ok(departmentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable("id") long id,
            @Valid @RequestBody DepartmentDTO departmentDTO,
            BindingResult result
    ) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            departmentResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(departmentResponse);
        }
        Department department = departmentService.updateDepartment(id, departmentDTO);
        departmentResponse.setDepartment(department);
        return ResponseEntity.ok(departmentResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");
    }

//    @PostMapping("/generateFake")
    private ResponseEntity<String> generateFakeData(){
        Faker faker = new Faker();
        for(int i=1; i<= 150;i++){
            String departmentName = faker.company().name();
            String departmentCode = faker.code().ean8();
            if(!departmentService.existsByName(departmentName) && !departmentService.existsByDepartmentCode(departmentCode)){
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setName(departmentName);
                departmentDTO.setDepartmentCode(departmentCode);
                departmentDTO.setAddress(faker.address().fullAddress());
                departmentService.createDepartment(departmentDTO);
            }
        }
        return ResponseEntity.ok("Fake data generated");
    }
}

