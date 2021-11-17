package com.geekylikes.app.repositories;

import com.geekylikes.app.models.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
