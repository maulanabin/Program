package com.doyatama.university.repository;

import com.doyatama.university.model.SubjectGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
    @Override
    Optional<SubjectGroup> findById(Long id);
}
