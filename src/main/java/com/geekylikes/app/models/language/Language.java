package com.geekylikes.app.models.language;

import com.fasterxml.jackson.annotation.*;
import com.geekylikes.app.models.developer.Developer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String tag;


//    @ManyToMany
//    @JoinTable(
//            name="developer_language",
//            joinColumns = @JoinColumn(name = "language_id"),
//            inverseJoinColumns = @JoinColumn(name = "developer_id")
//    )
//    @JsonIgnoreProperties("languages")
//    private Set<Developer> developers = new HashSet<>();

    public Language() {
    }

    public Language(String name, String tag) {
        this.name = name;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
