package kr.co.koo.thymeleaf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//Thymeleaf + spring-boot => Model 갱장희 중요 (extends, implement, AOP)
@Controller
public class thymeleaf_controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Thymeleaf ${T}로 static 변수 로드 가능
	public static String kk = "연습";		//static 변수 선언시 서버재가동 필요 
	
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
	
	//thymeleaf 언어 사용법 (기본)
	@GetMapping("/thymeleaf.do")
	public String thymeleaf(Model m, HttpServletRequest req) {
		
		String code = "관리자 리스트 <br> <b>일반관리자</b>";
		m.addAttribute("code",code);

		//키배열
		Map<String, Object> all = new HashMap<>();
		all.put("mid", "koo");
		all.put("mage", "27");
		m.addAttribute("all",all);
		
		//검색어 
		String search = "수원 1창고";
		m.addAttribute("search",search);
		
		//url
		String page = "http://naver.com";
		m.addAttribute("page",page);
		
		//session
		HttpSession session = req.getSession();
		session.setAttribute("muser", "구나영");
		
		//조건값 
		String result="ok";
		m.addAttribute("result",result);
		
		
		return "/thymeleaf.html";
	}
	
	//thymeleaf 언어 사용법 (중급)
		@GetMapping("/thymeleaf2.do")
		public String thymeleaf2(Model m, HttpServletRequest req) {
			int paymethod = 1;
			m.addAttribute("paymethod",paymethod);
			
			//클래스 배열을 타임리프로 출력 
			ArrayList<String> alldata = new ArrayList<>();
			alldata.add("검정");
			alldata.add("분홍");
			alldata.add("노랑");
			alldata.add("초록");
			alldata.add("빨강");
			m.addAttribute("alldata",alldata);
			
			//라디오 체크박스 핸들링
			String agree = "Y";
			m.addAttribute("agree",agree);
			
			//select 핸들링
			String level = "일반회원";
			m.addAttribute("level",level);
			
			//PC 시간 
			//문자열로 보내면 #temporals 사용불가능
			String today = LocalDateTime.now().toString();
			System.out.println(today);
			m.addAttribute("today",today);
			
			//바로 보내야 사용 가능 
			m.addAttribute("today2",LocalDateTime.now());
			
			Date to = new Date();
			m.addAttribute("today3",to);
			
			//
			Random rd = new Random();
			int no = rd.nextInt(10);
			System.out.println(no);
			
			
			
			return "/thymeleaf2.html";
		}
		
		@GetMapping("/thymeleaf3.do")
		public String thymeleaf3 (Model m) {
			/*
			데이터 값을 출력하는 역할 
			List<String> a = new ArrayList<>();
			a.add("aa");
			a.add("bb");
			a.add("cc");
			a.add("dd");
			a.add("ee");
			m.addAttribute("result",a);

			Map<String, Object> a = new HashMap<>();
			a.put("data1", "aa");
			a.put("data2", "bb");
			a.put("data3", "cc");
			a.put("data4", "dd");
			*/
			
			//Form name, id, value 설정
			m.addAttribute("result", this.all_inject);
			m.addAttribute("act_url", "/thymeleaf4.do");
			m.addAttribute("money", 90000);
			m.addAttribute("money2", 22.52);
			
			return "/thymeleaf3.html";
		}

}
