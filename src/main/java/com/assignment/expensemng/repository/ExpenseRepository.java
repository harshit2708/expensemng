package com.assignment.expensemng.repository;

import com.assignment.expensemng.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    public List<Expense> findAllByIsSettled(Boolean isSettled);
}
