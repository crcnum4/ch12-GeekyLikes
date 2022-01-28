package com.geekylikes.app.payloads.response;

import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.language.Language;

import java.util.HashSet;
import java.util.Set;

public class SelfDeveloper {
    private Long id;
    private String name;
    private String email;
    private Integer cohort;
    private Set<Language> languages;
    private Set<Developer> friends; //approved
    private Set<Developer> pendingFriends; // i've sent
    private Set<Developer> incomingFriends; // waiting fore me

    public SelfDeveloper(Long id, String name, String email, Integer cohort, Set<Language> languages, Set<Developer> friends, Set<Developer> pendingFriends, Set<Developer> incomingFriends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cohort = cohort;
        this.languages = languages;
        this.friends = friends;
        this.pendingFriends = pendingFriends;
        this.incomingFriends = incomingFriends;
    }

    static public SelfDeveloper build(Developer developer) {
        Set<Developer> empty = new HashSet<>();
        return new SelfDeveloper(
                developer.getId(),
                developer.getName(),
                developer.getEmail(),
                developer.getCohort(),
                developer.getLanguages(),
                empty,
                empty,
                empty
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCohort() {
        return cohort;
    }

    public void setCohort(Integer cohort) {
        this.cohort = cohort;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Developer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Developer> friends) {
        this.friends = friends;
    }

    public Set<Developer> getPendingFriends() {
        return pendingFriends;
    }

    public void setPendingFriends(Set<Developer> pendingFriends) {
        this.pendingFriends = pendingFriends;
    }

    public Set<Developer> getIncomingFriends() {
        return incomingFriends;
    }

    public void setIncomingFriends(Set<Developer> incomingFriends) {
        this.incomingFriends = incomingFriends;
    }
}
