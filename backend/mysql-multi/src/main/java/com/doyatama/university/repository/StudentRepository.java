package com.doyatama.university.repository;

import com.doyatama.university.model.Student;
import com.doyatama.university.model.Subject;
import com.doyatama.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByName(String username);
    Optional<Student> findById(Long id);
}
