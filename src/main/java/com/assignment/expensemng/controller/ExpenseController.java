package com.assignment.expensemng.controller;

import com.assignment.expensemng.entity.Expense;
import com.assignment.expensemng.service.ExpenseService;
import com.assignment.expensemng.service.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    public ExpenseController(ExpenseServiceImpl expenseServiceImpl) {
        this.expenseService = expenseServiceImpl;
    }

    //GET ALL EXPENSES
    @GetMapping("/expenses")
    public List<Expense> getExpenses() throws IOException {
        List<Expense> expenses = expenseService.getExpenses();
        return expenses;
    }

    //GET EXPENSES BY ID
    @GetMapping("/expenses/{id}")
    public Optional<Expense> getExpenseByID(@PathVariable("id") Long expenseID) throws IOException {
        return expenseService.getExpensesByID(expenseID);
    }

    //POST Expenses PDF
    @PostMapping("/expenses")
    public String uploadExpenses(@RequestParam("file") MultipartFile file) throws IOException {
        //return "done";
        String msg = expenseService.parseExpensesPdf(file);
        return msg;

    }
    // GET AMOUNT PENDING TO BE CLAIMED
    @GetMapping("/expenses/pending")
    public Double getPedingAmount(){
        List<Expense> expenseList = expenseService.getNotSettledExpenses();

        Double pendingAmount = 0.0;
        for (Expense expense:expenseList ) {
            System.out.println(expense);
            pendingAmount += expense.getAmount();
        }
        System.out.println("Pending amount = " + pendingAmount);

        return pendingAmount;

    }
/*
    @PutMapping("/expenses/pending")
    public String setAllPendingSettlementAsSettled(){
        expenseService.setAllPendingSettlementAsSettled();
    }

 */
}
