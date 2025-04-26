package kr.co.koo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service에 implements api_mapper 쓰면 작살남
//implement 사용할수 있는 사항은 Service라는 인터페이스를 생성후 오버라이딩 형태로 사용시 가능
//mapper 인터페이스 로드시 재귀함수돼버림 엉망진창!!!
@Service
public class api_service{
	
	@Autowired
	api_mapper am;	//interface 로드 
	
	public int insert_cms(Map<String, String> map) {
		int result = this.am.api_insert(map);
		return result;
	}

}
