package com.geekylikes.app.repositories;

import com.geekylikes.app.models.Geekout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeekoutRepository extends JpaRepository<Geekout, Long> {
}
