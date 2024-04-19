package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.config.DecisionEngineConstants;
import ee.taltech.inbankbackend.exceptions.InvalidLoanAmountException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanPeriodException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanValidatorTest {

    private final LoanValidator loanValidator = new LoanValidatorImpl();

    @Test
    public void shouldValidateLoanAmount() {
        long validAmount = 9000;
        assertDoesNotThrow(() -> loanValidator.validateLoanAmount(validAmount));
    }

    @Test
    public void shouldValidateMaxLoanAmount() {
        long maxLoanAmount = DecisionEngineConstants.MAXIMUM_LOAN_AMOUNT;
        assertDoesNotThrow(() -> loanValidator.validateLoanAmount(maxLoanAmount));
    }

    @Test
    public void shouldValidateMinLoanAmount() {
        long minLoanAmount = DecisionEngineConstants.MINIMUM_LOAN_AMOUNT;
        assertDoesNotThrow(() -> loanValidator.validateLoanAmount(minLoanAmount));
    }

    @Test
    public void shouldNotValidateLoanAmount() {
        long invalidLoadAmount = 100000;
        assertThrows(InvalidLoanAmountException.class,
                () -> loanValidator.validateLoanAmount(invalidLoadAmount));
    }

    @Test
    public void shouldValidateLoanPeriod() {
        int validPeriod = 14;
        assertDoesNotThrow(() -> loanValidator.validateLoanPeriod(validPeriod));
    }

    @Test
    public void shouldValidateMaxLoanPeriod() {
        int maxLoanPeriod = DecisionEngineConstants.MAXIMUM_LOAN_PERIOD;
        assertDoesNotThrow(() -> loanValidator.validateLoanPeriod(maxLoanPeriod));
    }

    @Test
    public void shouldValidateMinLoanPeriod() {
        int minLoanPeriod = DecisionEngineConstants.MINIMUM_LOAN_PERIOD;
        assertDoesNotThrow(() -> loanValidator.validateLoanPeriod(minLoanPeriod));
    }

    @Test
    public void shouldNotValidateLoanPeriod() {
        int invalidPeriod = 65;
        assertThrows(InvalidLoanPeriodException.class,
                () -> loanValidator.validateLoanPeriod(invalidPeriod));
    }
}
