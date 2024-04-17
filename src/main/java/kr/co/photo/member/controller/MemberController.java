package kr.co.photo.member.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.photo.member.service.MemberService;

@Controller

public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberService service;
	
	// 메인 페이지
	@RequestMapping(value = "/")
	public String home() {
		logger.info("메인 페이지 접속");
		return "login";
	}
	
	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpSession session, String id, String pw) {
		logger.info(id+" / "+pw);
		String page = "login";
		String loginId = service.login(id,pw);
		
		if(loginId != null) {
			logger.info("로그인 성공");
			page = "redirect:/list";
			session.setAttribute("loginId", loginId);
		}else {
			logger.info("로그인 실패");
			model.addAttribute("msg","아이디 또는 패스워드를 확인해 주세요.");
		}
		
		return page; 
	}
	
}
