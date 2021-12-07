package com.geekylikes.app.repositories;

import com.geekylikes.app.models.approve.Approve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApproveRepository extends JpaRepository<Approve, Long> {
}
