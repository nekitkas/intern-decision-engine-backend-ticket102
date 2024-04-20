package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.exceptions.OverageException;
import ee.taltech.inbankbackend.exceptions.UnderageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdValidatorTest {

    private final IdValidator idValidator = new IdValidatorImpl();
    private String underagePersonalCode;
    private String overagePersonalCode;
    private String validPersonalCode;

    @BeforeEach
    void setUp() {
        underagePersonalCode = "50901082555";
        overagePersonalCode = "34501083535";
        validPersonalCode = "38411266610";
    }

    @Test
    public void testUnderagePersonalCodeValidation() {
        assertThrows(UnderageException.class,
                () -> idValidator.validateAge(underagePersonalCode));
    }

    @Test
    public void testOveragePersonalCodeValidation() {
        assertThrows(OverageException.class,
                () -> idValidator.validateAge(overagePersonalCode));
    }

    @Test
    public void testValidPersonalCodeValidation() {
        assertDoesNotThrow(() -> idValidator.validateAge(validPersonalCode));
    }
}
