package com.engineer.batchprocessing.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Table(name = "user_details", schema = "schema1", catalog = "schema1")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "department")
    private String department;

    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "salary")
    private Double salary;

    public Integer getUserId () {
        return userId;
    }

    public void setUserId (Integer userId) {
        this.userId = userId;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public Integer getAge () {
        return age;
    }

    public void setAge (Integer age) {
        this.age = age;
    }

    public String getDepartment () {
        return department;
    }

    public void setDepartment (String department) {
        this.department = department;
    }

    public String getJoiningDate () {
        return joiningDate;
    }

    public void setJoiningDate (String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public
    Double getSalary () {
        return salary;
    }

    public
    void setSalary (Double salary) {
        this.salary = salary;
    }
}
