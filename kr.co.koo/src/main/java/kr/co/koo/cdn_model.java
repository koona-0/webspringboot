package kr.co.koo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@Repository("cdn_model")
public class cdn_model {
	//FTP 정보를 외부에서 수정하지 못하게 설정
	final String user = "testuser";
	final String pass = "a10041004!";
	final int port = 21;
	FTPClient fc = null;
	FTPClientConfig fcc = null;
	
	boolean result = false;	//FTP 전송 결과 true(정상) false(오류발생)
	
	@Autowired
	private cdn_service cs;
	
	//@Resource로 Controller에서 호출과 Model에서 호출이 다름 => 각각 new해버리는 상황
	@Resource(name="file_DTO")
	file_DTO file_DTO;
	
	// CDN Server로 해당 파일을 전송하는 역할을 담당한 Model
	public boolean cdn_ftp(MultipartFile files, String url) throws Exception{
		try {
			//신규파일명 
			String new_file = rename_file(files.getOriginalFilename());
//			System.out.println(new_file);
			
			this.fc = new FTPClient();
			this.fc.setControlEncoding("utf-8");
			this.fcc = new FTPClientConfig();
			
			this.fc.configure(fcc);
			this.fc.connect(url, this.port);	//FTP 연결 
			if(this.fc.login(this.user, this.pass)) {	//FTP 로그인 
				//BINARY_FILE_TYPE : 이미지, 동영상, PDF, ZIP...
				this.fc.setFileType(FTP.BINARY_FILE_TYPE);
				//FTP 디렉토리 경로를 설정 후 해당 파일을 Byte로 전송 
				this.result = this.fc.storeFile("/koona0/"+new_file, files.getInputStream());
				
				System.out.println(files.getOriginalFilename());
				
				//DB 저장 
				this.file_DTO.setORI_FILE(files.getOriginalFilename());
				this.file_DTO.setNEW_FILE(new_file);
				
				
				//html 이미지태그 주소에 .jpg이런거 쓰면 CDN쓰는거 아님 !!! 주의  
				String api_nm[] = new_file.split("[.]");// .으로 스플릿하면 인식못함 .은 모든글자를 의미 [.]이나 \\.으로 사용 
				System.out.println("apinm : "+Arrays.toString(api_nm));
				
				
				this.file_DTO.setAPI_FILE(api_nm[0]);	//그래서 속성명 날리고 넣어야함
				String api_url = "http://"+url+"/koona0/"+new_file;	//https일때 https로 쓰기 
				this.file_DTO.setFILE_URL(api_url);
				
				//Database에 insert부분 문제 발생시 false
				int result2 = this.cs.cdn_insert(this.file_DTO);
				if(result2 > 0) {
					this.result = true;
				}else {
					this.result = false;
				}
				
			}

		} catch (Exception e) {
			System.out.println("model e: "+e);
		} finally {
			this.fc.disconnect();	//FTP Service 연결을 종료 
		}

		return this.result;
	}
	
	//실제 파일명을 개발자가 원하는 이름으로 변경하는 메소드 
	public String rename_file(String ori_file) {
		//오늘날짜+시간
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = sf.format(today);
		//난수 
		int no = (int)Math.ceil(Math.random()*10);
		
		//파일의 속성명을 담는 변수 
		String type = ori_file.substring(ori_file.lastIndexOf("."));
		
		//개발자가 원하는 파일명으로 변경완료 코드
		String new_file = day + no + type;
		return new_file;
	}

}
