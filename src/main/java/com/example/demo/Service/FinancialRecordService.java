package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.FinancialRecordRequest;
import com.example.demo.dto.FinancialRecordResponse;
import com.example.demo.entity.TransactionType;

public interface FinancialRecordService {
	
	FinancialRecordResponse create(FinancialRecordRequest request, String email);
    List<FinancialRecordResponse> getAll(TransactionType type, String category, LocalDate from, LocalDate to);
    FinancialRecordResponse update(Long id, FinancialRecordRequest request);
    void delete(Long id);

}
