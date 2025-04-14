package kr.co.koo;

import java.util.List;

//mapper.java와 동일하게 작성 
//@Service에서는 해당 class에서 @Override 메소드를 실행함 => @Controller에서 interface 로드 
public interface membership_service {
	
	public List<membership_dto> alldata();
//	public List<membership_dto> onedata(String MID);

	int join_memer(membership_dto dto);

}
