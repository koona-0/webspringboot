//A개발자의 입력값을 받아서 B개발자가 Ajax 통신을 적용 후 결과값을 리턴 

function apink_love(cid,cname){
	var result;
	if(cid==""||cname==""){	//A개발자 데이터를 비정상적으로 보낼 경우 
		result = "no";		
	}else{	//정상적으로 데이터를 받을 경우 
		fetch("http://172.30.1.31:8080/ajax2.do",{
			method:"POST",
			body : new URLSearchParams("CID="+cid+"&CNAME="+cname)
		}).then(function(aa){
			return aa.text();			
		}).then(function(bb){
			if(bb=="ok"){	//B개발자 컨트롤러에서 SQL결과를 받음
				result="ok";	//A개발자에게 경과값을 전달하기 위한 변수값
			}else{
				result="error";	//B개발자 컨트롤러에서 SQL오류발생시 결과값
			}
		}).catch(function(error){
			console.log("통신오류발생");
		});
		
		result = "ok";
	}
	
	return result;	//A개발자에게 다시 회신 
	
	//console.log(cid);
	//console.log(cname);
}