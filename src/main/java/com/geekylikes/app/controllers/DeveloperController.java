package com.geekylikes.app.controllers;

import com.geekylikes.app.models.avatar.Avatar;
import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.geekout.Geekout;
import com.geekylikes.app.repositories.AvatarRepository;
import com.geekylikes.app.repositories.DeveloperRepository;
import com.geekylikes.app.repositories.GeekoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/developers")
public class DeveloperController {
    @Autowired
    private DeveloperRepository repository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    GeekoutRepository geekoutRepository;

    @GetMapping
    public @ResponseBody List<Developer> getDevelopers() {
        return repository.findAll();
    }

    @GetMapping("/lang/{langId}")
    public List<Developer> getDevsByLanguage(@PathVariable Long langId) {
        return repository.findAllByLanguages_id(langId);
    }

    @GetMapping("/cohort/{cohort}")
    public ResponseEntity<List<Developer>> getDevelopersByCohort(@PathVariable Integer cohort) {
        return new ResponseEntity<>(repository.findAllByCohort(cohort, Sort.by("name")), HttpStatus.OK);
    }

    @GetMapping("/likes/{devId}")
    public List<Geekout> getApprovedGeekouts(@PathVariable Long devId) {
        return geekoutRepository.findAllByApprovals_developer_id(devId);
    }

    @GetMapping("/{id}")
    public @ResponseBody Developer getOneDeveloper(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer newDeveloper) {
        return new ResponseEntity<>(repository.save(newDeveloper), HttpStatus.CREATED);
    }

    @PostMapping("/photo")
    public Developer addPhoto(@RequestBody Developer dev) {
        Developer developer = repository.findById(dev.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // check if developer has an avatar and if so, delete or modify existing avatar before creating new.
        if (developer.getAvatar() != null) {
            Avatar avatar = developer.getAvatar();
            avatar.setUrl(dev.getAvatar().getUrl());
            avatarRepository.save(avatar);
            return developer;
        }
        Avatar avatar = avatarRepository.save(dev.getAvatar());
        developer.setAvatar(avatar);
        return repository.save(developer);

    }

    @PutMapping("/language")
    public Developer addLanguage(@RequestBody Developer updates) {
        Developer developer = repository.findById(updates.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        developer.languages.addAll(updates.languages);
        return repository.save(developer);
    }

    @PutMapping("/{id}")
    public @ResponseBody Developer updateDeveloper(@PathVariable Long id, @RequestBody Developer updates) {
        Developer developer = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        updates.setId(developer.getId());
//        return repository.save(updates);
        if (updates.getName() != null) developer.setName(updates.getName());
        if (updates.getEmail() != null) developer.setEmail(updates.getEmail());
        if (updates.getCohort() != null) developer.setCohort(updates.getCohort());
        if (updates.languages != null) developer.languages = updates.languages;

        return repository.save(developer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroyDeveloper(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


}
