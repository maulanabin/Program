package com.doyatama.university.repository;

import com.doyatama.university.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface UjianRepository extends JpaRepository<Exam, Long> {
    @Override
    Optional<Exam> findById(Long id);
}
