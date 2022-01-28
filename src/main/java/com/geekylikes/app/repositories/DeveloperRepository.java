package com.geekylikes.app.repositories;

import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.language.Language;
import com.geekylikes.app.models.relationship.ERelationship;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    List<Developer> findAllByCohort(Integer cohort, Sort sort);
    List<Developer> findAllByLanguages_id(Long id);

    //get a list of developers that liked geekout
    List<Developer> findAllByApprovals_geekout_id(Long id);

    Optional<Developer> findByUser_id(Long id);

    Void deleteByUser_id(Long id);

//    Set<Developer> findAllByRelationships_type(ERelationship type);
//    Set<Developer> findAllByInverseRelationships_type(ERelationship type);

//    @Query("SELECT * FROM developer WHERE cohort = ?1 AND ?2 in languages")
//    Developer findByCohortAndLanguage(Integer cohort, String language);
}
