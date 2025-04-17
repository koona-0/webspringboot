package kr.co.koo.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.annotation.Resource;
import kr.co.koo.cdn_service;

@Controller
public class main2_controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Controller에서 setter값을 입력받은 것들을 AOP getter로 받아서 추가 코드를 작성하게 됨 
	@Resource(name="user_dto")
	user_dto dto;
	
	@GetMapping("/testaop.do")
	public String testaop(Model m) {
		
		String mid = "koo";
		String mname = "구나영";
		
		this.dto.setMid(mid);
		this.dto.setMname(mname);
		
		if(mid.equals("koo")) {
			this.log.info("아이디를 확인하였습니다.");
		}
		
		m.addAttribute("mname",mname);
		
		return null;
	}
	
	@GetMapping("/testaop2.do")
	public String testaop2() {
		this.log.info("test2가 실행되는 컨트롤러");
		return null;
	}
	
	@GetMapping("/testaop_logout.do")
	public String testaop_logout(Model m, @SessionAttribute(name="userid", required = false) String userid) {
		this.log.info("로그아웃 컨트롤러");
		
		this.dto.setMid(userid);
		
		return null;
	}
	
	
	

}
