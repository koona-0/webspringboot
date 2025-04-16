package kr.co.koo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

//CDN 전용 컨트롤러 
@Controller
public class cdn_controller {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="file_DTO")
	file_DTO file_DTO;
	
	@Resource(name="cdn_model")
	cdn_model cdn_model;
	
	//CDN Server로 파일 전송 및 DB 저장 
	@PostMapping("/cdn/cdn_fileok.do")
	public String cdn_fileok(
			@RequestParam(name="mfile") MultipartFile mfile[],
			@RequestParam(name="url") String url
			) throws Exception{	
		//처음부터 배열로 받아야 나중에 기획에 파일 여러개로 받는거로 바뀌었을때 편함 
		
		try {
			//사용자가 업로드한 파일명을 개발자가 원하는 이름으로 변경 후 FTP 전송
			boolean result = this.cdn_model.cdn_ftp(mfile[0], url);
			
			if(result==true) {
				this.log.info("저장완료");
			}else {
				this.log.info("저장실패");
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			
		}
		
		return null;
	}
	
}