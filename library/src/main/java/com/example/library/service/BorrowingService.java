package com.example.library.service;

import com.example.library.payloads.BorrowingDTO;

public interface BorrowingService {
    BorrowingDTO addBorrowing(String isbn, String memberId);
}
