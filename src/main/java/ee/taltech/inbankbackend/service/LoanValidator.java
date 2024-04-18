package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.exceptions.InvalidLoanAmountException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanPeriodException;

public interface LoanValidator {
    void validateLoanAmount(Long loanAmount) throws InvalidLoanAmountException;
    void validateLoanPeriod(int loanPeriod) throws InvalidLoanPeriodException;
}
