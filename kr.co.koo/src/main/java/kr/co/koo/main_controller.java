package kr.co.koo;

import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.resource.HttpResource;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class main_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	PrintWriter pw = null;
	
	@Autowired
	private membership_service ms;
	
	/*
	@Autowired
	private membership_service2 ms2;
	 */
	
	@Resource(name="membership_service2")
	private membership_service2 ms2;

	
	@Resource(name="membership_dto")
	membership_dto dto;
	
	@GetMapping("/index.do")
	public String index(Model m) {
		String username="구나영";
		m.addAttribute("username", username);
		
//		this.dto.getMIDX;
		
		List<membership_dto> all = this.ms.alldata();
		this.log.info(all.get(0).getMID().toString());
		System.out.println(all.size());
		
		
		return null;
		
	}
	
	@GetMapping("/index2.do")
	public String index2(Model m) {
		String mid = "777";
		
		List<membership_dto> one = this.ms2.onedata(mid);
		this.log.info(one.get(0).getMID().toString());
		
		return null;
		
	}
	
	//멀티맵핑 : 둘다받기 가능 
	@RequestMapping(value={"/join.do","/join"}, method=RequestMethod.POST)
	public String join(@ModelAttribute("cp") membership_dto dto2,
			HttpServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
//		this.log.info(dto2.getMID());
		try {
			this.pw = res.getWriter();
			
			int result = this.ms.join_memer(dto2);
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('신규회원가입 완료');"
						+ "location.href='./login.do';"
						+ "</script>");
				this.log.info("성공");
				
			}else {
				this.pw.print("<script>"
						+ "alert('신규회원가입 실패 : db');"
						+ "istory.go(-1);"
						+ "</script>");
				this.log.info("실패");
			}
			
			
		}catch (Exception e) {
			this.log.info(e.toString());
		}finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
}
