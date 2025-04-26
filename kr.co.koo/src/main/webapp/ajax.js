var ea = document.querySelectorAll(".pd");	//. = 클래스

document.querySelector("#all").addEventListener("click",function(){
	//console.log(this.checked);
	// querySelectorAll : 오브젝트가 원시배열 
	for(var a = 0; a < ea.length; a++){
		ea[a].checked = this.checked;
	}
	
});

function ajax(){	//ajax 전달 
	let count = 0;	//check 갯수 파악 
	var formdata = new FormData();
	for(var a = 0; a < ea.length; a++){
		if(ea[a].checked ==true){
			formdata.append("product",ea[a].value);
			count++;
		}
	}
	if(count==0){
		alert("하나 이상 선택해주세요");
	}else{	//ajax post 통신 
		//console.log(formdata.getAll("product"));	//잘들어갔나 확인 
		
		var http, result;
		http = new XMLHttpRequest();
		http.onreadystatechange = function(){
			if(http.readyState==4 && http.status==200){
				result = this.response;
				if(result=="ok"){
					alert("정상적으로 데이터 처리");
				}else{
					alert("데이터 처리 오류 발생");
					
				}
				
			}
			
		}
		//보안이 중요하니까 키를 헤더에 담아달라고 프론트한테 말해야함 ~!
		http.open("POST","./ajax.do",true);
		http.setRequestHeader("mkey","a123456");	//보안키를 헤더에 담아 보내기 
		http.send(formdata);
		
		//http.setRequestHeader("content-type","application/json"); 으로 보낸경우 컨트롤러에서 바디로 받아야함
		//http.send("product="+JSON.stringify(변수));
		
		//http.setRequestHeader("content-type","application/x-www-form-urlencode"); 으로 보낸경우 
		//http.send("product="+변수);
			 
		
		
	}
	
}