package com.bytes.thinkr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Kent on 1/10/2016.
 */
@Entity
public class Test {

    private Long id;

    private String firstname;

    private String lastname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String first) {
        this.firstname = first;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String last) {
        this.lastname = last;
    }
}
