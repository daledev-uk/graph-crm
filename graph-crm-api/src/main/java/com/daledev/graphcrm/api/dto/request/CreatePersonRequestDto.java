package com.daledev.graphcrm.api.dto.request;

import com.daledev.graphcrm.api.dto.detail.PersonDto;

import java.io.Serializable;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
public class CreatePersonRequestDto implements Serializable {
    private PersonDto person;

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }
}
