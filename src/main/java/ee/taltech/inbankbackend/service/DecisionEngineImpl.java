package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.config.DecisionEngineConstants;
import ee.taltech.inbankbackend.exceptions.InvalidLoanAmountException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanPeriodException;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;
import ee.taltech.inbankbackend.exceptions.NoValidLoanException;
import org.springframework.stereotype.Service;

/**
 * A service class that provides a method for calculating an approved loan amount and period for a customer.
 * The loan amount is calculated based on the customer's credit modifier,
 * which is determined by the last four digits of their ID code.
 */
@Service
public class DecisionEngineImpl implements DecisionEngine {

    private final IdValidator idValidator;
    private final CreditModifierCalc creditModifierCalc;
    private final LoanValidator loanValidator;

    public DecisionEngineImpl(
            IdValidator idValidator,
            CreditModifierCalc creditModifierCalc,
            LoanValidator loanValidator
    ) {
        this.idValidator = idValidator;
        this.creditModifierCalc = creditModifierCalc;
        this.loanValidator = loanValidator;
    }

    /**
     * Calculates the maximum loan amount and period for the customer based on their ID code,
     * the requested loan amount and the loan period.
     * The loan period must be between 12 and 60 months (inclusive).
     * The loan amount must be between 2000 and 10000€ months (inclusive).
     *
     * @param personalCode ID code of the customer that made the request.
     * @param loanAmount Requested loan amount
     * @param loanPeriod Requested loan period
     * @return A Decision object containing the approved loan amount and period, and an error message (if any)
     * @throws InvalidPersonalCodeException If the provided personal ID code is invalid
     * @throws InvalidLoanAmountException If the requested loan amount is invalid
     * @throws InvalidLoanPeriodException If the requested loan period is invalid
     * @throws NoValidLoanException If there is no valid loan found for the given ID code, loan amount and loan period
     */
    public Decision calculateApprovedLoan(String personalCode, Long loanAmount, int loanPeriod)
            throws InvalidPersonalCodeException, InvalidLoanAmountException, InvalidLoanPeriodException,
            NoValidLoanException {
        try {
            verifyInputs(personalCode, loanAmount, loanPeriod);
            int outputLoanAmount;
            int creditModifier = creditModifierCalc.getCreditModifier(personalCode);

            if (creditModifier == 0) {
                throw new NoValidLoanException("No valid loan found!");
            }

            while (highestValidLoanAmount(loanPeriod, creditModifier) < DecisionEngineConstants.MINIMUM_LOAN_AMOUNT) {
                loanPeriod++;
            }

            if (loanPeriod <= DecisionEngineConstants.MAXIMUM_LOAN_PERIOD) {
                outputLoanAmount = Math.min(DecisionEngineConstants.MAXIMUM_LOAN_AMOUNT, highestValidLoanAmount(loanPeriod, creditModifier));
            } else {
                throw new NoValidLoanException("No valid loan found!");
            }

            return new Decision(outputLoanAmount, loanPeriod, null);
        } catch (Exception e) {
            return new Decision(null, null, e.getMessage());
        }
    }

    /**
     * Calculates the largest valid loan for the current credit modifier and loan period.
     *
     * @return Largest valid loan amount
     */
    private int highestValidLoanAmount(int loanPeriod, int creditModifier) {
        return creditModifier * loanPeriod;
    }

    /**
     * Verify that all inputs are valid according to business rules.
     * If inputs are invalid, then throws corresponding exceptions.
     *
     * @param personalCode Provided personal ID code
     * @param loanAmount Requested loan amount
     * @param loanPeriod Requested loan period
     * @throws InvalidPersonalCodeException If the provided personal ID code is invalid
     * @throws InvalidLoanAmountException If the requested loan amount is invalid
     * @throws InvalidLoanPeriodException If the requested loan period is invalid
     */
    private void verifyInputs(String personalCode, Long loanAmount, int loanPeriod)
            throws InvalidLoanPeriodException, InvalidLoanAmountException, InvalidPersonalCodeException {

        idValidator.isValid(personalCode);
        loanValidator.validateLoanAmount(loanAmount);
        loanValidator.validateLoanPeriod(loanPeriod);
    }
}