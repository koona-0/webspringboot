package kr.co.koo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@Repository("cdn_model")
public class cdn_model {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
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
				
//				System.out.println(files.getOriginalFilename());

				//Oracle : CLOB(Ascii), BLOB(Binary)
				this.file_DTO.setFILE_BIN(files.getBytes());
				
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
	
	// CDN Server에 있는 해당 파일을 삭제하는 역할 model
	public boolean cdn_ftpdel(String newfiles, String idx) throws Exception {
		this.fc = new FTPClient();
		this.fcc = new FTPClientConfig();
		this.fc.configure(this.fcc);
		this.fc.connect("kbsn.or.kr", this.port);
		this.fc.enterRemotePassiveMode(); // 패시브모드로 전환 (FTP 접속환경)
		// PassiveMode : 가상 포트를 이용하여 연결을 설정
		
		//this.fc.removeDirectory("/koona0");	//디렉토리 통째로 삭제 
		//this.fc.makeDirectory("/koona1");		//디렉토리 생성 (오늘날짜의 폴더 만들기같은거)
		
		//removeDirectory : 디렉토리 삭제 
		//makeDirectory : 디렉토리 생성
		// deleteFile : FTP에 접속된 서버에서 해당 경로에 있는 파일을 삭제하는 class
		
        /*
        //기존 에러발생 코드 
		this.fc.deleteFile("/koona0/" + newfiles);
		
		if(this.result ==true) {	//정상적으로 FTP 파일을 삭제 한 후 
			int r = this.cs.cdn_delete(idx);
		}
		*/

		if (this.fc.login(this.user, this.pass)) { // 로그인

			// 파일 존재하는지 확인
			if (isFileExistsOnFtp(newfiles)) { // 파일이 ftp에 있으면
				this.result = this.fc.deleteFile("/koona0/" + newfiles); // ftp에서 삭제

				if (this.result) {	//ftp에서 삭제됐으면 
					int r = this.cs.cdn_delete(idx);	//db에서 삭제 
					if (r > 0) {
						this.result = true;
					} else {
						this.result = false;
					}
				}
			}

			else { // 파일이 ftp에 없으면
				int r = this.cs.cdn_delete(idx); // DB에서 삭제
				if (r > 0) {
					this.result = true;
				} else {
					this.result = false;
				}
			}

		}

		return this.result;
	}
	
	// FTP Server에 해당 파일이 있는지 확인하는 메소드
	public boolean isFileExistsOnFtp(String filePath) throws Exception {
	    FTPClient tempFc = new FTPClient();
	    FTPClientConfig tempFcc = new FTPClientConfig();
	    tempFc.configure(tempFcc);
	    tempFc.connect("kbsn.or.kr", this.port);
	    boolean exists = false;

	    try {
	        if (tempFc.login(this.user, this.pass)) {
	            tempFc.enterLocalPassiveMode(); // 또는 tempFc.enterRemotePassiveMode(); 환경에 따라 선택
	            FTPFile[] files = tempFc.listFiles("/koona0/");
	            if (files != null) {
	                for (FTPFile file : files) {
	                    if (file.isFile() && file.getName().equals(filePath)) {
	                        exists = true;
	                        break;
	                    }
	                }
	            }
	            tempFc.logout();
	        }
	    } catch (Exception e) {
	        log.error("FTP 파일 존재 확인 오류: {}", e.getMessage());
	        throw e;
	    } finally {
	        if (tempFc.isConnected()) {
	            try {
	                tempFc.disconnect();
	            } catch (Exception e) {
	                log.error("FTP 연결 종료 오류: {}", e.getMessage());
	            }
	        }
	    }
	    return exists;
	}

}













