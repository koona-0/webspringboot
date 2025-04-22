package kr.co.koo.pay;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.inicis.std.util.SignatureUtil;

import jakarta.annotation.Resource;

@Controller
public class pay_controller {

	@Resource(name = "pay_DTO")
	pay_DTO dto;

	@GetMapping("/pay1.do")
	public String pay1(Model m) {
		Random rd = new Random();

		// 상품코드 난수번호 0~99 숫자중 하나로 설정
		this.dto.setProduct_code(String.valueOf(rd.nextInt(100)));
		this.dto.setProduct_money("1000");
		this.dto.setPrice("1000");

		m.addAttribute("act_url", "./pay2.do");
		m.addAttribute("dto", this.dto);
		return "/pay1.html";
	}

	// 결제할 고객 데이터를 입력하는 메소드
	@PostMapping("/pay2.do")
	public String pay2(@ModelAttribute pay_DTO pd, Model m) {
//		System.out.println(pd.getPrice());

		m.addAttribute("pd", pd);

		return "/pay2.html";
	}
	
	//결제 시스템 API 가동 및 결제진행
		@PostMapping("/pay3.do")
		public String pay3(@ModelAttribute pay_DTO pd, Model m) throws Exception{
			String data1 = pd.getProduct_code();	//상품코드
			String data2 = pd.getPrice();	//가격
			String mid = "INIpayTest";
			String signKey = "SU5JTElURV9UUklQTEVERVNfS0VZU1RS";
			String goodcode = data1; 
			String mKey = SignatureUtil.hash(signKey, "SHA-256");
			String timestamp = SignatureUtil.getTimestamp();
			String orderNumber = mid+"_"+goodcode;	//고유값=>카드영수증에 출력되는 오더번호
			String price = data2;
			Map<String, String> signParam = new HashMap<String,String>();
			signParam.put("oid", orderNumber);
			signParam.put("price", price);
			signParam.put("timestamp", timestamp);
			String signature = SignatureUtil.makeSignature(signParam);		
					
			m.addAttribute("mid",mid);
			m.addAttribute("mKey", mKey);
			m.addAttribute("orderNumber",orderNumber);	//oid
			m.addAttribute("timestamp",timestamp);
			m.addAttribute("signature",signature);
			m.addAttribute("pd",pd);
			
			return "/pay3.html";
		}

}
