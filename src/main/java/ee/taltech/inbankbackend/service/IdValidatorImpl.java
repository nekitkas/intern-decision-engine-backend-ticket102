package ee.taltech.inbankbackend.service;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeParser;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;
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
    public Period getAge(String personalCode) throws PersonalCodeException {
        return parser.getAge(personalCode);
    }

    @Override
    public boolean isValid(String personalCode) throws InvalidPersonalCodeException {
        if (!validator.isValid(personalCode)) {
            throw new InvalidPersonalCodeException("Invalid personal ID code!");
        }

        return true;
    }
}
