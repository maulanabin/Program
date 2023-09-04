package com.doyatama.university.repository;

import com.doyatama.university.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Override
    Optional<Answer> findById(Long id);
}
