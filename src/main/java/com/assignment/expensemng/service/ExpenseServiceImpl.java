package com.assignment.expensemng.service;

import com.assignment.expensemng.entity.Expense;
import com.assignment.expensemng.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public String parseExpensesPdf(MultipartFile file) throws IOException {
        // Parse the expenses PDF and extract the relevant data
        //ExpenseSummary expenseSummary = new ExpenseSummary();

        // Load the PDF document
        //PDFBoxResource pdfResource = new PDFBoxResource(file.getInputStream());

        PDDocument document = Loader.loadPDF(file.getInputStream());

        // Get the first page of the PDF document
        //PDPage page = document.getPage(0);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        // Extract the text from the page
        String text = pdfStripper.getText(document);

        // Split the text into lines
        String[] lines = text.split("\n");

        // Iterate over all the lines on the page
        for (String line : lines) {

            // Check if the line contains the word "Expense"
            if (line.contains("Expense")) {

                // Split the line into three parts: the expense type, the expense amount, and the expense description
                String[] parts = line.split(":", 3);

                // Get the expense type
                String type;
                try {
                    type = parts[0].trim();
                } catch (Exception e) {
                    return "provide expense type";
                }

                // Get the expense amount
                Double amount;
                try {
                    amount = Double.valueOf(parts[1].trim());
                } catch (Exception e) {
                    return "provide expense amount";
                }

                if(amount < 0){
                    return "Amount cannot be negative";
                }
                // Get the expense description
                String description;
                try {
                    description = parts[2].trim();
                } catch (Exception e) {
                    return "provide expense description";
                }

                // Create a new Expense object
                @Valid Expense expense = new Expense();
                expense.setType(type);
                expense.setAmount(amount);
                expense.setDescription(description);
                expense.setIsSettled(false);

                expenseRepository.save(expense);
                // Add the expense to the expense summary
                //expenseSummary.addExpense(expense);
            }

        }

        // Close the PDF document
        document.close();

        // Return the expense summary
        return "expenses add successfully";
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }


    public Optional<Expense> getExpensesByID(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<Expense> getNotSettledExpenses() {
        return expenseRepository.findAllByIsSettled(false);
    }

    /*
    @Override
    public void setAllPendingSettlementAsSettled() {
        Lis
    }
    */


}
