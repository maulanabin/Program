package com.doyatama.university.repository;

import com.doyatama.university.model.Department;
import com.doyatama.university.model.StudyProgram;
import com.doyatama.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    Optional<StudyProgram> findByName(String username);
    Optional<StudyProgram> findById(Long id);
}
