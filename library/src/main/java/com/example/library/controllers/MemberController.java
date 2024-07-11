package com.example.library.controllers;

import com.example.library.model.Member;
import com.example.library.payloads.BookDTO;
import com.example.library.payloads.MemberDTO;
import com.example.library.repository.MemberJpaRepository;
import com.example.library.service.BookService;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getAllMembers()
    {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDTO> addMember(@RequestBody @Valid MemberDTO memberDTO)
    {
        return new ResponseEntity<>(memberService.addMember(memberDTO),HttpStatus.CREATED);
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable String memberId, @RequestBody MemberDTO memberDTO)
    {
        return new ResponseEntity<>(memberService.updateMember(memberId, memberDTO), HttpStatus.OK);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<MemberDTO> deleteMember(@PathVariable String memberId)
    {
        return new ResponseEntity<>(memberService.deleteMember(memberId),HttpStatus.OK);
    }

    @GetMapping("/admin/members/borrow/")
    public ResponseEntity<List<MemberDTO>> borrowedMemberList(
            @RequestParam(name = "value",defaultValue = "0", required = false) Long value)
    {
        return new ResponseEntity<>(memberService.memberBorrowingMoreThanTwo(value),HttpStatus.OK);
    }
}
