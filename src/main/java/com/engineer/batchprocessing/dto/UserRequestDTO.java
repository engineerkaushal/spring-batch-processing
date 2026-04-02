package com.engineer.batchprocessing.dto;

import com.engineer.batchprocessing.customAnnotation.ValidateUserAnnotation;
import jakarta.validation.constraints.Email;

public class UserRequestDTO {

    @ValidateUserAnnotation(message = "User name can't empty")
    private String userName;

    @ValidateUserAnnotation(message = "Email id can't empty or null")
    @Email
    private String email;

    @ValidateUserAnnotation(message = "Password must be greater then 12 char"
            , minLength = 13)
    private String password;

    @ValidateUserAnnotation(message = "Age is required and must be 18+ and less than 100"
            , minValue = 18
            , maxValue = 100)
    private Integer age;

    public
    String getUserName () {
        return userName;
    }

    public
    void setUserName (String userName) {
        this.userName = userName;
    }

    public
    String getEmail () {
        return email;
    }

    public
    void setEmail (String email) {
        this.email = email;
    }

    public
    String getPassword () {
        return password;
    }

    public
    void setPassword (String password) {
        this.password = password;
    }

    public
    Integer getAge () {
        return age;
    }

    public
    void setAge (Integer age) {
        this.age = age;
    }
}
