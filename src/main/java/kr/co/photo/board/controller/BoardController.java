package kr.co.photo.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.photo.board.dto.BoardDTO;
import kr.co.photo.board.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService service;
	
	// list페이지 
	@RequestMapping(value="/list")
	public String list(Model model, HttpSession session) {
		logger.info("list요청");
		String page = "login";
		String id = (String) session.getAttribute("loginId");
		
		if(id != null) {
			page = "list";
			List<BoardDTO> list = service.list();
			model.addAttribute("list",list);
			model.addAttribute("loginBox", "<div>안녕하세요." + id + " 님!<a href='logout'>로그아웃</a></div>");
		}else {
			model.addAttribute("msg","로그인이 필요한 서비스 입니다.");
		}
		
		return page;
	}
	
	// 삭제
	@RequestMapping(value = "/del")
	public String del(HttpSession session, int idx) {
		String page = "redirect:/";
		logger.info("delete idx="+idx);
		
		if(session.getAttribute("loginId") != null) {
			page = "redirect:/list";
			service.del(idx);
		}
		
		return page;
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		logger.info("logout");
		return "redirect:/";
	}
	
	// 글쓰기 페이지 이동
	@RequestMapping(value = "/writeForm")
	public String writeForm() {
		logger.info("글쓰기 페이지 이동");
		return "writeForm";
	}
	
	// 글쓰기 
	@RequestMapping(value="/write")
	public String write(HttpSession session, @RequestParam Map<String, String> param) {
		String page = "redirect:/list";
		
		if(session.getAttribute("loginId") != null) {
			page = "writeForm";
			int row = service.write(param);
			if(row < 1) {
				page ="redirect:/list";
			}
		}
		
		return page;
	}
	
	// 상세보기
	@RequestMapping(value="/detail")
	public String detail(Model model, HttpSession session, String idx) {
		String page = "redirect:/list";
		
		if(session.getAttribute("loginId") != null) {
			page = "detail";
			BoardDTO dto = service.detail(idx);
			model.addAttribute("bbs",dto);
		}
		
		return page;
	}
	
	// 수정하기
	@RequestMapping(value = "/updateForm")
	public String updateForm(String idx, HttpSession session, Model model) {
		String page = "redirect:/list";
		logger.info("updateFrom idx="+idx);
	
		if(session.getAttribute("loginId") != null) {
			BoardDTO dto = service.updateForm(idx);
			model.addAttribute("bbs", dto);
			page = "updateForm";
		}
		
		return page;
	}
	
	// 수정 완료 
	@RequestMapping(value = "/update")
	public String update(HttpSession session, @RequestParam Map<String, String> param) {
		String page = "redirect:/list";
		logger.info("param : {}", param);
		
		if(session.getAttribute("loginId") != null) {
			service.update(param);
			page = "redirect:/detail?idx="+param.get("idx"); // 하나의 스트링 문장
		}
		
		return page;
	}
	
	
}
