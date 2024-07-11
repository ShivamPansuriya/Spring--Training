package com.example.library.service;

import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Member;
import com.example.library.payloads.BorrowingDTO;
import com.example.library.payloads.MemberDTO;
import com.example.library.repository.MemberJpaRepository;
import com.example.library.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.IconUIResource;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Override
    public List<MemberDTO> getAllMembers() {
        var members =  memberRepository.findAll();

        return members.stream().map(member -> modelMapper.map(member, MemberDTO.class)).toList();
    }

    @Override
    public MemberDTO addMember(MemberDTO memberDTO) {
        var member = modelMapper.map(memberDTO, Member.class);
        member.setMemberId(UUID.randomUUID().toString().substring(0,20));
        var savedBook = memberRepository.save(member);
        return modelMapper.map(savedBook, MemberDTO.class);
    }

    @Override
    @Transactional
    public MemberDTO updateMember(String memberId, MemberDTO memberDTO) {
        var member = memberRepository.findMemberByMemberId(memberId);

        if(member.isEmpty())
            throw new ResourceNotFoundException("Member","MemberId",memberId);

        member.get().setName(memberDTO.getName());

        var updatedMember = memberRepository.save(member.get());

        return modelMapper.map(updatedMember,MemberDTO.class);
    }

    @Override
    public MemberDTO deleteMember(String memberId) {
        var member = memberRepository.findMemberByMemberId(memberId);

        if(member.isEmpty())
            throw new ResourceNotFoundException("Member","memberId",memberId);

        memberRepository.delete(member.get());

        return modelMapper.map(member.get(), MemberDTO.class);
    }

    public MemberDTO getMemberById(String memberId)
    {
        var member = memberRepository.findMemberByMemberId(memberId);

        if(member.isEmpty())
            throw new ResourceNotFoundException("Member","memberId",memberId);

        return modelMapper.map(member.get(),MemberDTO.class);
    }
    public List<MemberDTO> memberBorrowingMoreThanTwo(Long value){
        return memberJpaRepository
                .memberBorrowingMoreThanTwo(value).stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .toList();
    }
}
