package kr.co.koo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
//컨트롤러에서 사용하는 방법 두가지
//1. 레포지토리 리소스 이용 : Controller @Resource로 로드 
//2. 오토와이어드 이용 

//JPA => @Service 위주로 사용	//@Repository와 @JPARepository가 충돌 
//JPA => @Repository 무조건 사용하고 싶을 경우 class extends 하여 제공 

@Repository("membership_service2")
public class membership_service2 {
	
	@Autowired
	private mapper mp;
	
	//사용자 아이디에 맞는 데이터 한개의 값 가져오는 메소드
	public List<membership_dto> onedata(String MID){
		List<membership_dto> one = this.mp.onedata(MID);
		
		return null;
	}
	

}
