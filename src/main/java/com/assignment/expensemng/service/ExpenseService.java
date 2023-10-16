package com.assignment.expensemng.service;

import com.assignment.expensemng.entity.Expense;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    public String parseExpensesPdf(MultipartFile file) throws IOException;

    public List<Expense> getExpenses();

    public Optional<Expense> getExpensesByID(Long id);

    public List<Expense> getNotSettledExpenses();

    //void setAllPendingSettlementAsSettled();
}
