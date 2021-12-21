package com.geekylikes.app.controllers;

import com.geekylikes.app.models.approve.Approve;
import com.geekylikes.app.models.auth.User;
import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.geekout.Geekout;
import com.geekylikes.app.repositories.ApproveRepository;
import com.geekylikes.app.repositories.DeveloperRepository;
import com.geekylikes.app.repositories.GeekoutRepository;
import com.geekylikes.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/geekouts")
public class GeekoutController {
    @Autowired
    private GeekoutRepository repository;

    @Autowired
    private ApproveRepository approveRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Geekout>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Geekout getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Geekout> createOne(@RequestBody Geekout geekout) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
//        System.out.println(geekout.getDeveloper().getId());
        Developer currentDeveloper = developerRepository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        geekout.setDeveloper(currentDeveloper);

        return new ResponseEntity<>(repository.save(geekout), HttpStatus.CREATED);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Geekout> likeById(@PathVariable Long id, @RequestBody Developer developer) {
        Optional<Geekout> geekout = repository.findById(id);

        if (geekout.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Approve newApproval = new Approve(developer, geekout.get());
        approveRepository.save(newApproval);
        return new ResponseEntity<>(geekout.get(), HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Geekout> updateOneById(@RequestBody Geekout geekout, @PathVariable Long id) {
//        similar to developer update.
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<List<Geekout>> getByDevId(@PathVariable Long devId) {
        return new ResponseEntity<>(repository.findByDeveloperId(devId), HttpStatus.OK);
    }


}
