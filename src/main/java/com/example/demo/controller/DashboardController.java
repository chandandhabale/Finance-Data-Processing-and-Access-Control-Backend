package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.FinancialRecordRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final FinancialRecordRepository recordRepository;

   
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<Map<String, Object>> getSummary() {
        BigDecimal income = recordRepository.getTotalIncome();
        BigDecimal expense = recordRepository.getTotalExpense();
        BigDecimal net = income.subtract(expense);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", income);
        summary.put("totalExpense", expense);
        summary.put("netBalance", net);
        return ResponseEntity.ok(summary);
    }

    
    @GetMapping("/by-category")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getByCategory() {
        List<Object[]> raw = recordRepository.getCategoryWiseTotals();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> item = new HashMap<>();
            item.put("category", row[0]);
            item.put("total", row[1]);
            result.add(item);
        }
        return ResponseEntity.ok(result);
    }

   
    @GetMapping("/trends")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getTrends() {
        List<Object[]> raw = recordRepository.getMonthlyTrends();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", row[0]);
            item.put("year", row[1]);
            item.put("type", row[2]);
            item.put("total", row[3]);
            result.add(item);
        }
        return ResponseEntity.ok(result);
    }

   
    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<?> getRecent() {
        return ResponseEntity.ok(recordRepository.findTop5ByDeletedFalseOrderByDateDesc());
    }
}
