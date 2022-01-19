package com.geekylikes.app.payloads.response;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.geekylikes.app.models.avatar.Avatar;
import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.language.Language;

import java.util.Set;

public class PublicDeveloper {
    private Long id;
    private String name;
    private Integer cohort;
    private Set<Language> languages;
    private Avatar avatar;

    public PublicDeveloper(Long id, String name, Integer cohort, Set<Language> languages, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.cohort = cohort;
        this.languages = languages;
        this.avatar = avatar;
    }

    static public PublicDeveloper build(Developer developer) {
        return new PublicDeveloper(
                developer.getId(),
                developer.getName(),
                developer.getCohort(),
                developer.getLanguages(),
                developer.getAvatar()
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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
