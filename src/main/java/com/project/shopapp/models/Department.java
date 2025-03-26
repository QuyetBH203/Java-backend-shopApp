package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code", nullable = false, length = 30,unique = true)
    private String departmentCode;

    @Column(name = "name", nullable = false, length = 100,unique = true)
    private String name;

    @Column(name = "address", nullable = false, length = 200)
    private String address;
}
