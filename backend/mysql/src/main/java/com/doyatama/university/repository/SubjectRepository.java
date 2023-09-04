package com.doyatama.university.repository;

import com.doyatama.university.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Override
    Optional<Subject> findById(Long id);
}
