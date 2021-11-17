package com.geekylikes.app.repositories;

import com.geekylikes.app.models.geekout.Geekout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeekoutRepository extends JpaRepository<Geekout, Long> {
    List<Geekout> findByDeveloperId(Long id);
}
