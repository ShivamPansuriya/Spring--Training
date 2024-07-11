package com.example.library.service;

import com.example.library.payloads.BorrowingDTO;
import com.example.library.payloads.MemberDTO;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMembers();
    MemberDTO addMember(MemberDTO memberDTO);
    MemberDTO updateMember(String memberId, MemberDTO memberDTO);
    MemberDTO deleteMember(String memberId);
    MemberDTO getMemberById(String memberId);
    List<MemberDTO> memberBorrowingMoreThanTwo(Long value);
}
