package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.DepartmentDTO;
import com.project.shopapp.models.Department;
import com.project.shopapp.responses.DepartmentListResponse;
import com.project.shopapp.responses.DepartmentResponse;
import com.project.shopapp.services.department.IDepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;
//    @GetMapping("")
//    public ResponseEntity<?> getDepartments() {
//        return ResponseEntity.ok().body("Departments");
//    }

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

    @GetMapping("")
    public ResponseEntity<?> getAllDepartments(
            @RequestParam(required = false) String departmentCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updatedAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<Department> departments = departmentService.searchDepartments(
                departmentCode, name, address, updatedAt, createdAt, pageRequest
        );
        List<Department> departmentsContent = departments.getContent();
        return ResponseEntity.ok().body(DepartmentListResponse.builder()
                .departments(departmentsContent)
                .totalPages(departments.getTotalPages()).build()
        );

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

