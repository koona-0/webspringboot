package kr.co.koo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cdn_service {

	@Autowired
	cdn_mapper mp;

	public int cdn_insert(file_DTO dto) {
		int result = this.mp.cdn_insert(dto);
		return result;
	}

	public List<file_DTO> all(Integer part, file_DTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("part", part);
		
		if(part == 1) {			//고유값으로 해당하는 데이터만 찾는 경우 
			map.put("AIDX", dto.getAIDX());
		}else if(part == 3) {	//검색어로 데이터를 찾는 경우 
			map.put("word", dto.getWord());
		}
		
		
		System.out.println(map);

		List<file_DTO> result = this.mp.cdn_select(map);
		return result;
	}

	public List<file_DTO> cdn_images(String APINO) {
		List<file_DTO> result = this.mp.cdn_images(APINO);
		return result;
	}
	
	
	int cdn_delete(String AIDX) {
		int result = this.mp.cdn_delete(AIDX);
		return result;
	}
	
	/*  AOP 로그 저장 테이블  */
	public int log_table(String mid) {
		int result = this.mp.log_table(mid);
		return result;
	}

}
