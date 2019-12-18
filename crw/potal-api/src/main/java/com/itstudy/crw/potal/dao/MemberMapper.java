package com.itstudy.crw.potal.dao;

import com.itstudy.crw.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);

    Member queryMembmerlogin(Map<String, Object> map);

    void updateAcctType(Member loginMember);

    void updateBasicinfo(Member loginMember);
}