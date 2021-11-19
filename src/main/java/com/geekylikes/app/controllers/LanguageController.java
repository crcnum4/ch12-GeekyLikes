package com.geekylikes.app.controllers;

import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.language.Language;
import com.geekylikes.app.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @Autowired
    private LanguageRepository repository;

    @GetMapping
    public List<Language> getAll() {
        return repository.findAll();
    }

    // use findbyid().orelsethrow normally
    @GetMapping("/{id}")
    public ResponseEntity<Language> getById(@PathVariable Long id) {
        Optional<Language> language = repository.findById(id);

        if(language.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(language.get(), HttpStatus.OK);
    }

    @PostMapping
    public Language createOne(@RequestBody Language newLanguage) {
        return repository.save(newLanguage);
    }


}
