package com.doyatama.university.repository;

import com.doyatama.university.model.Lecture;
import com.doyatama.university.model.Subject;
import com.doyatama.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByName(String username);
    Optional<Lecture> findById(Long id);
}
