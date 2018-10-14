package com.daledev.graphcrm.api.dto.detail;

import com.daledev.graphcrm.api.constants.Gender;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
public class PersonDto implements Serializable {
    private String uuid;
    private String title;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
