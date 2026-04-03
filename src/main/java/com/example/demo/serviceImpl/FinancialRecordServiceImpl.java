package com.example.demo.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Service.FinancialRecordService;
import com.example.demo.dto.FinancialRecordRequest;
import com.example.demo.dto.FinancialRecordResponse;
import com.example.demo.entity.FinancialRecord;
import com.example.demo.entity.TransactionType;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialRecordRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    private FinancialRecordResponse toResponse(FinancialRecord record) {
        FinancialRecordResponse response = new FinancialRecordResponse();
        response.setId(record.getId());
        response.setAmount(record.getAmount());
        response.setType(record.getType());
        response.setCategory(record.getCategory());
        response.setDate(record.getDate());
        response.setNotes(record.getNotes());
        response.setCreatedBy(record.getCreatedBy().getUsername());
        return response;
    }

    @Override
    public FinancialRecordResponse create(FinancialRecordRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        record.setCreatedBy(user);

        return toResponse(recordRepository.save(record));
    }

    @Override
    public List<FinancialRecordResponse> getAll(TransactionType type, String category, LocalDate from, LocalDate to) {
        List<FinancialRecord> records;

        if (type != null) {
            records = recordRepository.findByTypeAndDeletedFalse(type);
        } else if (category != null && !category.isBlank()) {
            
            records = recordRepository.findByCategoryIgnoreCaseAndDeletedFalse(category);
        } else if (from != null && to != null) {
            records = recordRepository.findByDateBetweenAndDeletedFalse(from, to);
        } else {
            records = recordRepository.findByDeletedFalse();
        }

        return records.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public FinancialRecordResponse update(Long id, FinancialRecordRequest request) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        if (record.isDeleted()) {
            throw new ResourceNotFoundException("Record not found with id: " + id);
        }

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());

        return toResponse(recordRepository.save(record));
    }

    @Override
    public void delete(Long id) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));
        record.setDeleted(true);
        recordRepository.save(record);
    }
}
