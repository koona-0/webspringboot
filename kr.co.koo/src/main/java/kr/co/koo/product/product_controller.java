package kr.co.koo.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//패키지 생성시 기존에 사용한 패키지명+신규패키지명으로 적용해야 적용됨 
@Controller
public class product_controller {
	
	@GetMapping("/product.do")
	public String product(Model m) {
		String product="냉장고";
		
		m.addAttribute("product", product);
		
		return null;
		
	}

}
