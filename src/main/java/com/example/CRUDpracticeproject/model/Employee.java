package com.example.CRUDpracticeproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String startDate;

    public Employee() {
    }

    @JsonCreator
    public Employee(Long id, String name, String startDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
    }

    @JsonGetter
    public Long getId() {
        return id;
    }

    @JsonSetter
    public void setId(Long id) {
        this.id = id;
    }

    @JsonGetter
    public String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter
    public String getStartDate() {
        return startDate;
    }

    @JsonSetter
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
