package com.geekylikes.app.models.developer;

import com.fasterxml.jackson.annotation.*;
import com.geekylikes.app.models.approve.Approve;
import com.geekylikes.app.models.avatar.Avatar;
import com.geekylikes.app.models.geekout.Geekout;
import com.geekylikes.app.models.language.Language;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
@Entity
public class Developer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private Integer cohort;
//    private String[] languages;

//    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
//    private List<Geekout> geekouts;

    @ManyToMany
    @JoinTable(
            name = "developer_language",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @JsonIgnoreProperties("developers")
    public Set<Language> languages = new HashSet<>();

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Approve> approvals;

    @OneToOne
    private Avatar avatar;

    public Developer() {}

    public Developer(String name, String email, Integer cohort) {
        this.name = name;
        this.email = email;
        this.cohort = cohort;
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

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

//    public List<Geekout> getGeekouts() {
//        return geekouts;
//    }
//
//    public void setGeekouts(List<Geekout> geekouts) {
//        this.geekouts = geekouts;
//    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
