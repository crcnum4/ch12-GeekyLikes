package com.geekylikes.app.models.geekout;

import com.fasterxml.jackson.annotation.*;
import com.geekylikes.app.models.approve.Approve;
import com.geekylikes.app.models.developer.Developer;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Geekout {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"languages", "email", "avatar"})
    private Developer developer;

    private String title;
    private String content;

    @OneToMany(mappedBy = "geekout", fetch = FetchType.LAZY)
    @JsonIncludeProperties("developer")
    private Set<Approve> approvals;

    public Geekout() {
    }

    public Geekout(Developer developer, String title, String content) {
        this.developer = developer;
        this.title = title;
        this.content = content;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Approve> getApprovals() {
        return approvals;
    }

    public void setApprovals(Set<Approve> approvals) {
        this.approvals = approvals;
    }
}
