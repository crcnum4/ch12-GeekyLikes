package com.geekylikes.app.controllers;

import com.geekylikes.app.models.auth.User;
import com.geekylikes.app.models.avatar.Avatar;
import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.geekout.Geekout;
import com.geekylikes.app.models.language.Language;
import com.geekylikes.app.models.relationship.ERelationship;
import com.geekylikes.app.models.relationship.Relationship;
import com.geekylikes.app.payloads.response.FriendDeveloper;
import com.geekylikes.app.payloads.response.PublicDeveloper;
import com.geekylikes.app.payloads.response.SelfDeveloper;
import com.geekylikes.app.repositories.*;
import com.geekylikes.app.security.services.UserDetailsImpl;
import com.geekylikes.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

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

    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public @ResponseBody List<Developer> getDevelopers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloperById(@PathVariable Long id) {
        // get user
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer currentDeveloper = repository.findByUser_id(currentUser.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Developer developer = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        // check friendship
        if (
                relationshipRepository.existsByOriginator_idAndRecipient_idAndType(
                        currentDeveloper.getId(), developer.getId(), ERelationship.ACCEPTED
                ) || relationshipRepository.existsByOriginator_idAndRecipient_idAndType(
                        developer.getId(), currentDeveloper.getId(), ERelationship.ACCEPTED
                )
        ) {

//            Set<Developer> developerFriends = repository.findAllByRelationships_type(ERelationship.ACCEPTED);
//            developerFriends.addAll(repository.findAllByInverseRelationships_type(ERelationship.ACCEPTED));
            return new ResponseEntity<>(FriendDeveloper.build(developer), HttpStatus.OK);
        }

        // TODO: if blocked send 404

        return new ResponseEntity<>(PublicDeveloper.build(developer), HttpStatus.OK);

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

    @GetMapping("/self")
    public @ResponseBody SelfDeveloper getSelf() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer currentDev =  repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return SelfDeveloper.build(currentDev);
    }

//    @GetMapping("/{id}")
//    public @ResponseBody Developer getOneDeveloper(@PathVariable Long id) {
//        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }


    @PostMapping
    public ResponseEntity<SelfDeveloper> createDeveloper(@RequestBody Developer newDeveloper) {

        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        //TODO add check for existing developer profile.
        newDeveloper.setUser(currentUser);

        Developer dev = repository.save(newDeveloper);

        return new ResponseEntity<>(SelfDeveloper.build(dev), HttpStatus.CREATED);
    }

    @PostMapping("/photo")
    public Developer addPhoto(@RequestBody Developer dev) { // TODO refactor dev to updates
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }

        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    public Developer addLanguage(@RequestBody List<Language> updates) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        developer.languages.addAll(updates);
        return repository.save(developer);
    }

    @PutMapping
    public @ResponseBody Developer updateDeveloper(@RequestBody Developer updates) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        updates.setId(developer.getId());
//        return repository.save(updates);
        if (updates.getName() != null) developer.setName(updates.getName());
        if (updates.getEmail() != null) developer.setEmail(updates.getEmail());
        if (updates.getCohort() != null) developer.setCohort(updates.getCohort());
//        if (updates.languages != null) developer.languages = updates.languages;

        return repository.save(developer);
    }

    @DeleteMapping
    public ResponseEntity<String> destroyDeveloper() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        repository.deleteByUser_id(currentUser.getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

}
