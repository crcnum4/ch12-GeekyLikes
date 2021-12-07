package com.geekylikes.app.models.approve;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.geekylikes.app.models.developer.Developer;
import com.geekylikes.app.models.geekout.Geekout;

import javax.persistence.*;

@Entity
public class Approve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Developer developer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "geekout_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Geekout geekout;

    //..more fields

    public Approve() {
    }

    public Approve(Developer developer, Geekout geekout) {
        this.developer = developer;
        this.geekout = geekout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Geekout getGeekout() {
        return geekout;
    }

    public void setGeekout(Geekout geekout) {
        this.geekout = geekout;
    }
}
