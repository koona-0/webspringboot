package kr.co.koo.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.koo.cdn_service;

@Aspect		//AOP라는 뜻 
@Component	//해당 클래스는 무조건 실행된다는 뜻  
public class main2_aop {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	HttpServletRequest req = null;
	HttpSession se = null;
	
	//@Aspect가 달려있어서 리소스를 가져와도 new 가 아니라 해당 메소드가 작동되면서 데이터를 같이 가져오기 때문에 기존거를 가져올 수 있게됨 
	@Resource(name="user_dto")
	user_dto dto;

	@Autowired
	cdn_service cs;
	
	//@After : 해당 컨트롤러가 작동된 후에 실행되는 AOP
	//@Before : 해당 컨트롤러가 작동되기 전에 실행되는 AOP
	
	//execution : 해당 패키지에 잇는 컨트롤러 및 매핑 메소드를 실행하는 명령어 
	//		* : 모든 패키지 중에서 
	//		뒤에 달린 쟤를 실행시켜 
	//		(..) : 그안의 매개변수까지 다 가져와서 실행시킨다는뜻
	
//	@After("execution(* kr.co.koo.aop.main2_controller.*(..))")
	@Before("execution(* kr.co.koo.aop.main2_controller.testaop(..))")	//해당 컨트롤러의 모든 메소드에 적용 
//	public void testaop_a() throws Throwable{	//Throwable : Exception의 엄마 //트라이캐치에 써도됨 
		public void testaop_a() {	
		try {
			this.log.info("AOP에서 실행되는 로그");
			this.req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			this.se = this.req.getSession();
//			this.log.info(mid);	//본코드의 변수 쓸 수 없음 ! 안되는 코드 
			this.log.info(this.dto.getMid());
			this.se.setAttribute("userid", this.dto.getMid());
			this.se.setAttribute("useremail", "koona0@naver.com");
			
			int result = this.cs.log_table(this.dto.getMid());
			if(result > 0) {
				this.log.info("로그인 로그 저장완료");
			}
			
			
		}catch (Throwable t) {	//Exception보다 이거를 추천! 
			t.printStackTrace();
		}
		
	}
	
	/* 실무에서는 접속환경, 로그아웃로그인 여부이런거도 들어감 */
	//로그아웃되고나서 언제 로그아웃 했는지를 DB에 insert 하도록 코드를 AOP로 작성 
	@After("execution(* kr.co.koo.aop.main2_controller.testaop_logout(..))") 
	public void testaop_b() {
		try {
			this.se.invalidate();
			String loid = this.dto.getMid()+"_out";
			int result = this.cs.log_table(loid);
			if(result > 0) {
				this.log.info("로그아웃 로그 저장완료");
			}
			
		}catch (Throwable t) {
			t.printStackTrace();
		}
		
	}

}
