package com.geekylikes.app.repositories;

import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
