package com.itstudy.crw.potal.serviceimpl;

import com.itstudy.crw.bean.Member;
import com.itstudy.crw.potal.dao.MemberMapper;
import com.itstudy.crw.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper ;


    public Member queryMembmerlogin(Map<String, Object> map) {
        return  memberMapper.queryMembmerlogin(map);
    }

    public void updateAcctType(Member loginMember) {
        memberMapper.updateAcctType(loginMember);
    }

    public void updateBasicinfo(Member loginMember) {
        memberMapper.updateBasicinfo(loginMember);
    }

}
