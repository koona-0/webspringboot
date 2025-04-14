package kr.co.koo;

import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;

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
//		this.log.info(all.get(0).getMID().toString());
//		System.out.println(all.size());
		
		
		return null;
		
	}
	
	@GetMapping("/index2.do")
	public String index2(Model m) {
		
		List<membership_dto> one = this.ms2.onedata("aa");
//		this.log.info(one.get(0).getMID().toString());
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
