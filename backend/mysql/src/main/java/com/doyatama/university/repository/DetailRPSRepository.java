package com.doyatama.university.repository;

import com.doyatama.university.model.DetailRps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Repository
public interface DetailRPSRepository extends JpaRepository<DetailRps, Long> {
    @Override
    Optional<DetailRps> findById(Long id);
}
