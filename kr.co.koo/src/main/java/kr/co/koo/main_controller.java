package kr.co.koo;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class main_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	PrintWriter pw = null;
	
	@Autowired
	private membership_service ms;
	
	/*
	@Autowired
	private membership_service2 ms2;
	 */
	
	@Resource(name="membership_service2")
	private membership_service2 ms2;

	
	@Resource(name="membership_dto")
	membership_dto dto;
	
	@GetMapping("/index.do")
	public String index(Model m) {
		String username="구나영";
		m.addAttribute("username", username);
		
//		this.dto.getMIDX;
		
		List<membership_dto> all = this.ms.alldata();
		this.log.info(all.get(0).getMID().toString());
		System.out.println(all.size());
		
		
		return null;
		
	}
	
	@GetMapping("/index2.do")
	public String index2(Model m) {
		String mid = "777";
		
		List<membership_dto> one = this.ms2.onedata(mid);
		this.log.info(one.get(0).getMID().toString());
		
		return null;
		
	}
	
	//멀티맵핑 : 둘다받기 가능 
	@RequestMapping(value={"/join.do","/join"}, method=RequestMethod.POST)
	public String join(@ModelAttribute("cp") membership_dto dto2,
			HttpServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
//		this.log.info(dto2.getMID());
		try {
			this.pw = res.getWriter();
			
			int result = this.ms.join_memer(dto2);
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('신규회원가입 완료');"
						+ "location.href='./login.do';"
						+ "</script>");
				this.log.info("성공");
				
			}else {
				this.pw.print("<script>"
						+ "alert('신규회원가입 실패 : db');"
						+ "istory.go(-1);"
						+ "</script>");
				this.log.info("실패");
			}
			
			
		}catch (Exception e) {
			this.log.info(e.toString());
		}finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//CDN 서버로 이미지 및 컨텐츠 파일 전송 메소드 
	@PostMapping("/cdn_uploadok.do")
	public String cdn_server(@RequestParam(defaultValue = "", name="mfile") MultipartFile mfile) {
		this.log.info(mfile.getOriginalFilename());
		
		//FTP 서버에 접속하는 역할을 하는 라이브러리 호출 
		FTPClient ftp = new FTPClient();				//CDN 서버 연결 
		ftp.setControlEncoding("utf-8");				//CDN 연결시 파일 전송 언어셋 
		FTPClientConfig cf = new FTPClientConfig();		//FTP 접속 클라이언트 객체 생성 
		
		try {
			String filenm = mfile.getOriginalFilename();	//front-end가 전송한 파일명을 저장
//			String host = "koona0.dothome.co.kr";	//CDN 접속 url 
			String host = "kbsn.or.kr";	//CDN 접속 url 
			
			//FTP 정보
//			String user = "koona0";
			String user = "testuser";
//			String pass = "kona0926!";
			String pass = "a10041004!";
			int port = 22;				//ftp 접속 포트번호 21, sftp 기본포트 22
			
			ftp.configure(cf);
			ftp.connect(host,port);
			
			if(ftp.login(user, pass)) {	//FTP 사용자가 맞을 경우
				//BINARY_FILE_TYPE : 이미지, 동영상, PDF, XLS
				//ASCII_FILE_TYPE : 텍스트 문서 (txt, html, js, css)
				ftp.setFileType(FTP.BINARY_FILE_TYPE);		//파일형태 : binary, ascii 
				int result = ftp.getReplyCode();			//CDN 서버에서 파일 업로드시 지연사항을 확인
				this.log.info("지연코드 : " + result);			//200 이면 정상 404나 500이면 오류 
				
				//서버 경로에 해당 파일을 업로드시 바이트단위로 전송하게됨 
				boolean ok = ftp.storeFile("/cdn_upload/"+filenm, mfile.getInputStream());
				
				if(ok == true) {
					this.log.info("업로드 완료");
				}else {
					this.log.info("파일 전송중 오류 발생");
				}
				/*
				  Server (localhost)
				  Java, Spring, Database, Spring-boot
				 
				  Server (Cloud Server)
				  Java, Spring, Database, Spring-boot
				  + Server에 대한 구조 및 FTP, SSH, SFTP
				  - Web Server
				  - Database Server
				  - CDN Server
				  - Mail Server
				  - Name Server
				  - Proxy Server
				 */
				
			}else {		//로그인 실패 
				this.log.info("ftp 정보가 올바르지 않아 CDN 서버에 접근 실패하였습니다.");
			}
		}catch (Exception e) {
			this.log.info(e.toString());
		}finally {
			try {
				ftp.disconnect();	//ftp 접속을 종료 
			}catch (Exception e) {
			}
		}
		
		return null;
	}
	
	//CDN API서버 (파라미터 값을 이용하여 API 서버에 있는 이미지를 출력)
	//@ResponseBody : 해당 Mapping 페이지에서 결과값을 출력함 => View가 없는 상황 => MVP
	@CrossOrigin(origins = "*", allowedHeaders = "*")	//CORS 방지 origins에 아이피써두면 특정아이피만 사용가능 
	@GetMapping("/cdn_listapi/{filenm}")
	public @ResponseBody byte[] cdn_listapi(
			@PathVariable String filenm
			) {
		
		byte[] img = null;	//Front-end에게 CDN 경로 이미지명을 전송 
		String imgurl = null;
		
		if(filenm.equals("cat")) {	//파일이름이 있을 경우
			//해당 파라미터에 맞는 Database의 정보를 확인 후 해당 정보가 있을 경우 DB에 저장된 경로를 변수로 이관 
			imgurl = "http://kbsn.or.kr/cdn_upload/cat.jpg";
			try {
				//http의 url 라이브러리를 이용하여 외부 접속 환경을 셋팅 
				URL url = new URL(imgurl);
				
				//http 프로토콜로 적용시 사용 
				HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
				//http 프로토콜로 적용시 HttpsURLConnection 사용
				
				InputStream is = httpcon.getInputStream();	//해당 이미지를 바이트로 가져옴 
				img = IOUtils.toByteArray(is);	//바이트 변수에 가져온 이미지 바이트 전체를 이관  
				
				is.close();
				httpcon.disconnect();
				
			}catch (Exception e) {
				this.log.info("해당 경로에 파일이 없습니다");
			}
		}else {//파일이름이 없을 경우 
			this.log.info("해당 경로에 파일이 없습니다");
		}
		
		return img;		//<img src="">태그로 해당 API 경로 및 파일명 사용시 이미지 출력 
	}
	
}



















