package com.engineer.batchprocessing.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "state_details")
public class States implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "country_id")
    private String countryId;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "country_name")
    private String countryName;


    public
    int getId () {
        return id;
    }

    public
    void setId (int id) {
        this.id = id;
    }

    public
    String getName () {
        return name;
    }

    public
    void setName (String name) {
        this.name = name;
    }

    public
    String getCountryId () {
        return countryId;
    }

    public
    void setCountryId (String countryId) {
        this.countryId = countryId;
    }

    public
    String getCountryCode () {
        return countryCode;
    }

    public
    void setCountryCode (String countryCode) {
        this.countryCode = countryCode;
    }

    public
    String getCountryName () {
        return countryName;
    }

    public
    void setCountryName (String countryName) {
        this.countryName = countryName;
    }

    @Override
    public
    String toString () {
        return "States{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId='" + countryId + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
