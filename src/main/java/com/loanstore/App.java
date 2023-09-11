package com.loanstore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Loan> loans = new ArrayList<>();

        try {
            Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dateFormat.parse("05/06/2023"), 0.01, dateFormat.parse("05/04/2023"), 0.01);
            Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 5000, dateFormat.parse("01/06/2023"), 0.01, dateFormat.parse("05/08/2023"), 0.01);
            Loan loan3 = new Loan("L3", "C2", "LEN2", 50000, 30000, dateFormat.parse("04/04/2023"), 0.02, dateFormat.parse("04/05/2023"), 0.02);
            Loan loan4 = new Loan("L4", "C3", "LEN2", 50000, 30000, dateFormat.parse("04/04/2023"), 0.02, dateFormat.parse("04/05/2023"), 0.02);

            loans.add(loan1);
            loans.add(loan2);
            loans.add(loan3);
            loans.add(loan4);

            LoanStore loanStore = new LoanStore(loans);

            // Check due dates and log alerts
            loanStore.checkDueDatesAndAlert();

            // Aggregate and print results
            System.out.println("Aggregation by Lender:");
            System.out.println(loanStore.aggregateByLender());

            System.out.println("Aggregation by Interest:");
            System.out.println(loanStore.aggregateByInterest());

            System.out.println("Aggregation by Customer ID:");
            System.out.println(loanStore.aggregateByCustomerId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}