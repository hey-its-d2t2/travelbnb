package com.travelbnb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favourite")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}