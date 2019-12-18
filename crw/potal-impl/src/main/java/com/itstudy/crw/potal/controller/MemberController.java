package com.itstudy.crw.potal.controller;

import com.itstudy.crw.bean.Member;
import com.itstudy.crw.potal.service.MemberService;
import com.itstudy.crw.util.AjaxResult;
import com.itstudy.crw.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService ;

	@RequestMapping("/accttype")
	public String accttype(){		
		return "member/accttype";
	}
	
	@RequestMapping("/basicinfo")
	public String basicinfo(){		
		return "member/basicinfo";
	}
	
	
	@RequestMapping("/apply")
	public String apply(){	
		/*
		Ticket ticket = TicketService.getTicket(memberid) ;
		
		if(ticket == null ){
		     ticket  = new Ticket(); //封装数据
			//TicketService.saveTicket(ticket);
			
		}else{
			String pstep = ticket.getPstep();
			
			if("accttype".equals(pstep)){
			
				return "rediect:/member/basicinfo.htm";
			} else if("basicinfo".equals(pstep)){
			
				return "rediect:/member/uploadCert.htm";
			}
		}
		*/
		
		return "member/basicinfo";
	}
	
	
	@ResponseBody
	@RequestMapping("/updateBasicinfo")
	public Object updateBasicinfo(HttpSession session, Member member) {
		AjaxResult result = new AjaxResult();
		
		try {
			
			// 获取登录会员信息
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setRealname(member.getRealname());
			loginMember.setCardnum(member.getCardnum());
		//	loginMember.setTel(member.getTel());
		
			// 更新账户类型			
			memberService.updateBasicinfo(loginMember);
			
			result.setSuccess(true);
		} catch( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;

	}
	
	/**
	 * 更新账户类型
	 */
	@ResponseBody
	@RequestMapping("/updateAcctType")
	public Object updateAcctType( HttpSession session, String accttype ) {
		AjaxResult result = new AjaxResult();
		
		try {
			
			// 获取登录会员信息
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setAccttype(accttype);
			// 更新账户类型
			memberService.updateAcctType(loginMember);
			
			result.setSuccess(true);
		} catch( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;

	}
	
}