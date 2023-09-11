package com.loanstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private LoanStore loanStore;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Loan> loans = new ArrayList<>();

        
        // Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dateFormat.parse("05/06/2023"), 0.01, dateFormat.parse("05/07/2023"), 0.01);
        // Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 5000, dateFormat.parse("01/06/2023"), 0.01, dateFormat.parse("05/08/2023"), 0.01);
        // Loan loan3 = new Loan("L3", "C2", "LEN2", 50000, 30000, dateFormat.parse("04/04/2023"), 0.02, dateFormat.parse("04/05/2023"), 0.02);
        
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dateFormat.parse("05/06/2023"), 0.01, dateFormat.parse("05/07/2023"), 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 20000, dateFormat.parse("01/06/2023"), 0.01, dateFormat.parse("05/08/2023"), 0.01);
        Loan loan3 = new Loan("L3", "C2", "LEN2", 50000, 30000, dateFormat.parse("04/04/2023"), 0.02, dateFormat.parse("04/05/2023"), 0.02);
        //Loan loan4 = new Loan("L4", "C2", "LEN2", 30000, 15000, dateFormat.parse("05/06/2023"), 0.02, dateFormat.parse("05/07/2023"), 0.02);

        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
        
        loanStore = new LoanStore(loans);
    }

    //chack for the valid loan 
    @Test
    public void testAddValidLoan() throws ParseException, LoanValidationException {
        Loan validLoan = new Loan("L4", "C3", "LEN3", 30000, 30000, dateFormat.parse("03/06/2023"), 0.015, dateFormat.parse("05/08/2023"), 0.01);
        loanStore.addLoan(validLoan);
        assertTrue(loanStore.getLoans().contains(validLoan));
    }

    //chack for the Invalid loan
    @Test
    public void testAddInvalidLoan() throws ParseException {
        Loan invalidLoan = new Loan("L5", "C4", "LEN4", 20000, 20000, dateFormat.parse("06/06/2023"), 0.02, dateFormat.parse("05/04/2023"), 0.01);
        try {
            loanStore.addLoan(invalidLoan);
            fail("Expected LoanValidationException, but it was not thrown");
        } catch (LoanValidationException e) {
            assertFalse(loanStore.getLoans().contains(invalidLoan));
        }
    }

    @Test
    public void testAggregateByLender() {
        Map<String, Double> aggregation = loanStore.aggregateByLender();
        assertEquals(30000.0, aggregation.get("LEN1"));
        assertEquals(30000.0, aggregation.get("LEN2"));
    }

    @Test
    public void testAggregateByInterest() {
        Map<String, Double> aggregation = loanStore.aggregateByInterest();
        assertEquals(30000.0, aggregation.get("0.01"));
        assertEquals(30000.0, aggregation.get("0.02"));
    }

    @Test
    public void testAggregateByCustomerId() {
        Map<String, Double> aggregation = loanStore.aggregateByCustomerId();
        assertEquals(30000.0, aggregation.get("C1"));
        assertEquals(30000.0, aggregation.get("C2"));
    }

    @Test
    public void testCheckDueDatesAndAlert() {
        loanStore.checkDueDatesAndAlert();
    }
}
