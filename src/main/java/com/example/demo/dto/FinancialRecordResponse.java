package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.demo.entity.TransactionType;
import lombok.Data;

@Data
public class FinancialRecordResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
    private String notes;
    private String createdBy;

}
