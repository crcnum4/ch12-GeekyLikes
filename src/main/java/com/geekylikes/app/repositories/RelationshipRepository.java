package com.geekylikes.app.repositories;

import com.geekylikes.app.models.relationship.ERelationship;
import com.geekylikes.app.models.relationship.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findAllByOriginator_id(Long id);
    Set<Relationship> findAllByOriginator_idAndType(Long id, ERelationship type);
    Set<Relationship> findAllByRecipient_idAndType(Long id, ERelationship type);
    Optional<Relationship> findByOriginator_idAndRecipient_id(long oId, Long rId);
    //list<relationship> findAllByOriginator_idOrRecipient_id(Long id, long id)
}
