package kr.co.koo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cdn_service {
	
	@Autowired
	cdn_mapper mp;
	
	public int cdn_insert(file_DTO dto) {
		int result = this.mp.cdn_insert(dto);
		System.out.println("service : "+result);
		return result;
	}
	
	
}
