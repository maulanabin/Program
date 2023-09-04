package com.doyatama.university.repository;

import com.doyatama.university.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    @Override
    Optional<Chapter> findById(Long id);
}
