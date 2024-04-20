package ee.taltech.inbankbackend.service;

import ee.taltech.inbankbackend.exceptions.*;

public interface DecisionEngine {
    Decision calculateApprovedLoan(String personalCode, Long loanAmount, int loanPeriod) throws InvalidPersonalCodeException, InvalidLoanAmountException, InvalidLoanPeriodException,
            NoValidLoanException, UnderageException, OverageException;
}
