package ee.taltech.inbankbackend.service;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeParser;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;
import ee.taltech.inbankbackend.config.DecisionEngineConstants;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;
import ee.taltech.inbankbackend.exceptions.OverageException;
import ee.taltech.inbankbackend.exceptions.UnderageException;
import org.springframework.stereotype.Component;

import java.time.Period;

@Component
public class IdValidatorImpl implements IdValidator {

    private final EstonianPersonalCodeValidator validator;
    private final EstonianPersonalCodeParser parser;

    public IdValidatorImpl() {
        this.validator = new EstonianPersonalCodeValidator();
        this.parser = new EstonianPersonalCodeParser();
    }

    @Override
    public boolean validateAge(String personalCode) throws PersonalCodeException, OverageException, UnderageException {
        Period age = parser.getAge(personalCode);
        if (age.getYears() > DecisionEngineConstants.MAXIMUM_ALLOWED_AGE) {
            throw new OverageException("Customer is above the minimum age limit for receiving a loan");
        } else if (age.getYears() < DecisionEngineConstants.MINIMUM_ALLOWED_AGE) {
            throw new UnderageException("Customer is below the minimum age limit for receiving a loan");
        }

        return false;
    }

    @Override
    public boolean isValid(String personalCode) throws InvalidPersonalCodeException {
        if (!validator.isValid(personalCode)) {
            throw new InvalidPersonalCodeException("Invalid personal ID code!");
        }

        return true;
    }
}
