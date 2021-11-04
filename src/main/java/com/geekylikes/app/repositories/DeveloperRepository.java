package com.geekylikes.app.repositories;

import com.geekylikes.app.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
