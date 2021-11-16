package com.geekylikes.app.repositories;

import com.geekylikes.app.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
