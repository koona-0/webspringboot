package kr.co.koo.thymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;

//Thymeleaf + spring-boot => Model 갱장희 중요 (extends, implement, AOP)
@Controller
public class thymeleaf_controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//AOP와 공용으로 사용하는 DTO
	@Resource(name="all_DTO")
	all_DTO all_inject;
	
	/*
	return null : webapp에서 찾음
	return "파일명" : webapp에서 파일명.jsp 찾음
	return "/파일명" : templates에서 파일명.jsp 찾음 
	return "/파일명.html" : templates에서 파일명.html 찾음
	
	WEB-INF의 파일을 불러오고싶다면 
	return "/WEB-INF/views/파일명"	이게 아니라  
	return "WEB-INF/views/파일명"		이렇게 써야함  
	*/
	
	//Thymeleaf View에 Model로 생성 후 .html에 값을 전달 
	@GetMapping("sample.do")
	public String sample(Model m) {
		String product = "냉장고";
		m.addAttribute("product",product);
		
		
		return "/sample.html";
		//Thymeleaf안의 jsp는 <% %>이게 안먹어서 코드가 페이지에 노출됨 
		//파일명만 jsp인거지 기존 jsp처럼 핸들링 불가능 
		
		/*
		"sample" : /sample.jsp 를 찾음
		"/sample" : 500 error (템플렛 안에 jsp있을 경우 출력)
		"sample.html" : sample.html.jsp를 찾음 
		"/sample.html" : 템플렛 안에 html있을 경우 출력
		왜? 프로퍼티스에 그렇게 설정했음
		*/
	}
	
	@GetMapping("sample2.do")
	public String sample2(Model m) {
		String menu = "관리자 등록";
		String copy = "Copyright 2025 WEB By Design...";
		
		m.addAttribute("menu",menu);
		m.addAttribute("copy",copy);
		return "/subpage.html";
	}
	
	//작업용파일
	/*
	@GetMapping("indextest.do")
	public String indextest(Model m) {
		
		return "/index.html";
	}
	*/
	
	
	@GetMapping("shop.do")
	public String shop(Model m) {	//실제 메인페이지 
		m.addAttribute("menulist",this.all_inject.getMenus());
		return "/main.html";
	}
	
	@GetMapping("shop_login.do")
	public String shop_login(Model m) {
		//해당 객체명으로 DTO에 있는 배열값을 타임리프를 이용해 html로 이관
		m.addAttribute("menulist",this.all_inject.getMenus());
		
		return "/login.html";
	}

}
