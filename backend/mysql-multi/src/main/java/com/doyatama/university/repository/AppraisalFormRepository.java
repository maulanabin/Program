package com.doyatama.university.repository;

import com.doyatama.university.model.AppraisalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface AppraisalFormRepository extends JpaRepository<AppraisalForm, Long> {
    @Override
    Optional<AppraisalForm> findById(Long id);
}
