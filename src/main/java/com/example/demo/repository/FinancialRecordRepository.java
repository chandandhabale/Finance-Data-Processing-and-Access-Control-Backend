package com.example.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.FinancialRecord;
import com.example.demo.entity.TransactionType;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByDeletedFalse();

    List<FinancialRecord> findByTypeAndDeletedFalse(TransactionType type);

    List<FinancialRecord> findByCategoryIgnoreCaseAndDeletedFalse(String category);

    List<FinancialRecord> findByDateBetweenAndDeletedFalse(LocalDate from, LocalDate to);

    
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = com.example.demo.entity.TransactionType.INCOME AND f.deleted = false")
    BigDecimal getTotalIncome();

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = com.example.demo.entity.TransactionType.EXPENSE AND f.deleted = false")
    BigDecimal getTotalExpense();

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.deleted = false GROUP BY f.category")
    List<Object[]> getCategoryWiseTotals();

    @Query("SELECT MONTH(f.date), YEAR(f.date), f.type, SUM(f.amount) FROM FinancialRecord f WHERE f.deleted = false GROUP BY YEAR(f.date), MONTH(f.date), f.type ORDER BY YEAR(f.date) DESC, MONTH(f.date) DESC")
    List<Object[]> getMonthlyTrends();

    List<FinancialRecord> findTop5ByDeletedFalseOrderByDateDesc();
}
