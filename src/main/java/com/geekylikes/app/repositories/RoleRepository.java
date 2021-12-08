package com.geekylikes.app.repositories;

import com.geekylikes.app.models.auth.ERole;
import com.geekylikes.app.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
