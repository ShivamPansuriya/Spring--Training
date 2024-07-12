package com.example.library.service;


import com.example.library.payloads.BorrowingDTO;
import com.example.library.payloads.DetailDTO;
import com.example.library.payloads.MemberDTO;

import java.util.List;
import java.util.Map;

public interface BorrowingService {
    BorrowingDTO addBorrowing(String isbn, String memberId);
    List<DetailDTO> borrowedList();
    BorrowingDTO returnBook(Long id);
}
