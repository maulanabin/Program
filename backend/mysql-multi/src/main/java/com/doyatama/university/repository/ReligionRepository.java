package com.doyatama.university.repository;

import com.doyatama.university.model.Religion;
import com.doyatama.university.model.Subject;
import com.doyatama.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReligionRepository extends JpaRepository<Religion, Long> {
    Optional<Religion> findByName(String username);
    Optional<Religion> findById(Long id);
}
