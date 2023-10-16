package com.assignment.expensemng.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String type;

    @NotNull
    private String description;

    @NotNull
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;

    private Boolean isSettled ;


    // getters and setters
}