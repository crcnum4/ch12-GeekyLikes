package com.geekylikes.app.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Developer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private Integer cohort;
//    private String[] languages;
    @OneToMany
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    private List<Geekout> geekouts;
    @ManyToMany
    @JoinTable(
            name = "developer_language",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    public Set<Language> languages;

    public Developer() {}

    public Developer(String name, String email, Integer cohort, List<Geekout> geekouts, Set<Language> languages) {
        this.name = name;
        this.email = email;
        this.cohort = cohort;
        this.geekouts = geekouts;
        this.languages = languages;
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

}
