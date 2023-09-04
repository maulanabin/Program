package com.doyatama.university.repository;

import com.doyatama.university.model.Department;
import com.doyatama.university.model.Subject;
import com.doyatama.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String username);
    Optional<Department> findById(Long id);
}
