package com.geekylikes.app.models.developer;

import com.fasterxml.jackson.annotation.*;
import com.geekylikes.app.models.approve.Approve;
import com.geekylikes.app.models.auth.User;
import com.geekylikes.app.models.avatar.Avatar;
import com.geekylikes.app.models.language.Language;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToMany()
    @JoinTable(
            name="relationship",
            joinColumns = @JoinColumn(name="originator_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="recipient_id", referencedColumnName = "id")
    )
    @WhereJoinTable(clause = "type = 'ACCEPTED'")
    @JsonIgnore
    private Set<Developer> relationships = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name="relationship",
            joinColumns = @JoinColumn(name="recipient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="originator_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @WhereJoinTable(clause = "type = 'ACCEPTED'")
    private Set<Developer> inverseRelationships = new HashSet<>();

    // pendingRelationships
    @ManyToMany()
    @JoinTable(
            name="relationship",
            joinColumns = @JoinColumn(name="originator_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="recipient_id", referencedColumnName = "id")
    )
    @WhereJoinTable(clause = "type = 'PENDING'")
    @JsonIgnore
    private Set<Developer> pendingRelationships = new HashSet<>();

    // incomingRelationships
    @ManyToMany()
    @JoinTable(
            name="relationship",
            joinColumns = @JoinColumn(name="recipient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="originator_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @WhereJoinTable(clause = "type = 'PENDING'")
    private Set<Developer> incomingRelationships = new HashSet<>();

    // blockedRelationships
    // inverseBlockedRelationships

    public Developer() {}

    public Developer(String name, String email, Integer cohort, User user) {
        this.name = name;
        this.email = email;
        this.cohort = cohort;
        this.user = user;
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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Developer> getRelationships() {
        return relationships;
    }

    public void setRelationships(Set<Developer> relationships) {
        this.relationships = relationships;
    }

    public Set<Developer> getInverseRelationships() {
        return inverseRelationships;
    }

    public void setInverseRelationships(Set<Developer> inverseRelationships) {
        this.inverseRelationships = inverseRelationships;
    }

    public Set<Approve> getApprovals() {
        return approvals;
    }

    public void setApprovals(Set<Approve> approvals) {
        this.approvals = approvals;
    }

    public Set<Developer> getPendingRelationships() {
        return pendingRelationships;
    }

    public void setPendingRelationships(Set<Developer> pendingRelationships) {
        this.pendingRelationships = pendingRelationships;
    }

    public Set<Developer> getIncomingRelationships() {
        return incomingRelationships;
    }

    public void setIncomingRelationships(Set<Developer> incomingRelationships) {
        this.incomingRelationships = incomingRelationships;
    }
}
