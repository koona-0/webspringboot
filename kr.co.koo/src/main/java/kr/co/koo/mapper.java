package kr.co.koo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

//맵퍼 사용 두가지 방법

//1. MVVM(요즘많이사용) : 1대N 
//	mapper.xml => mapper(interface) => service(interface) => dao
//	Controller => Service(interface) => dao

//2. MVC : 1대1 => 컨트롤러 1 뷰1
//	mapper.xml => mapper(interface) => dao
//	Controller => dao

// MVP : 1대1 (View와 Model에서 의존성이 없음) 
@Mapper
public interface mapper {
	//@Query("select * from MEMBERSHIP")	이런거로 바로때리기도됨 
	public List<membership_dto> alldata();
	public List<membership_dto> onedata(String MID);
	
	int save_member(membership_dto dto);

}
