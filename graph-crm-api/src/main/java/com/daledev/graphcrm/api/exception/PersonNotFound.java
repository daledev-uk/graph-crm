package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
public class PersonNotFound extends RuntimeException {
    /**
     * @param peronId
     */
    public PersonNotFound(String peronId) {
        super("Person not found with ID : " + peronId);
    }
}
