package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;

import java.time.Period;

public interface IdValidator {
    Period getAge(String personalCode) throws Exception;
    boolean isValid(String personalCode) throws InvalidPersonalCodeException;
}
