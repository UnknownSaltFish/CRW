package com.itstudy.crw.potal.service;

import com.itstudy.crw.bean.Member;

import java.util.Map;



public interface MemberService {

	Member queryMembmerlogin(Map<String, Object> map);

	void updateAcctType(Member loginMember);

	void updateBasicinfo(Member loginMember);

}
