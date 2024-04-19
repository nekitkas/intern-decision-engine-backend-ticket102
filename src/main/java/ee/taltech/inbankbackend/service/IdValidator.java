package ee.taltech.inbankbackend.service;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;
import ee.taltech.inbankbackend.exceptions.UnderageException;
import ee.taltech.inbankbackend.exceptions.OverageException;

import java.time.Period;

public interface IdValidator {
    boolean validateAge(String personalCode) throws OverageException, UnderageException, PersonalCodeException;
    boolean isValid(String personalCode) throws InvalidPersonalCodeException;
}
