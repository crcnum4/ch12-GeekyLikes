package com.geekylikes.app.repositories;

import com.geekylikes.app.models.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
