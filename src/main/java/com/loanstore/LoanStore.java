package com.loanstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class LoanStore {
    private List<Loan> loans;
    private Logger logger;

    //LoanStore constructor
    public LoanStore(List<Loan> loans) {
        this.loans = new ArrayList<>(loans);
        this.logger = Logger.getLogger(LoanStore.class.getName());
    }

    public List<Loan> getLoans() {
        return loans;
    }

    //add loan method storing load only if valid otherwise it will through error if payment date and due date is invalid 
    public void addLoan(Loan loan) throws LoanValidationException {
    if (loan.isValid()) {
        loans.add(loan);
    } else {
        rejectLoan(loan, "Payment date is greater than the due date");
        throw new LoanValidationException("Loan validation failed: Payment date is greater than the due date");
    }
}

    public void rejectLoan(Loan loan, String reason) {
        logger.warning("Loan ID " + loan.getLoanId() + " rejected: " + reason);
    }

    //aggregation on the remaining amount, Interest and Penalty by Lender,Interest and Customer ID
    public Map<String, Double> aggregateByLender() {
        Map<String, Double> result = new HashMap<>();
        for (Loan loan : loans) {
            result.put(loan.getLenderId(), result.getOrDefault(loan.getLenderId(), 0.0) + loan.getRemainingAmount());
        }
        return result;
    }

    public Map<String, Double> aggregateByInterest() {
        Map<String, Double> result = new HashMap<>();
        for (Loan loan : loans) {
            result.put(String.valueOf(loan.getInterestPerDay()), result.getOrDefault(String.valueOf(loan.getInterestPerDay()), 0.0) + loan.getRemainingAmount());
        }
        return result;
    }

    public Map<String, Double> aggregateByCustomerId() {
        Map<String, Double> result = new HashMap<>();
        for (Loan loan : loans) {
            result.put(loan.getCustomerId(), result.getOrDefault(loan.getCustomerId(), 0.0) + loan.getRemainingAmount());
        }
        return result;
    }

    //checking if Loan crosses the due date or not
    public void checkDueDatesAndAlert() {
        for (Loan loan : loans) {
            if (loan.getPaymentDate().after(loan.getDueDate())) {
                logger.warning("Loan ID " + loan.getLoanId() + " has crossed the due date.");
            }
        }
    }
}
