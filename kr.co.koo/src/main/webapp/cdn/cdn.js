export class cdn_lists {
	cdn_listdel() {
		//querySelector : 한 개의 오브젝트를 가져옴
		//querySelectorAll : 한 개 이상의 같은 이름을 가진 오브젝트를 가져오는 역할 
		var ob = document.querySelectorAll("#ls .ck");	//ls아이디 안에 ck클래스 끌고옴
		console.log(ob);

		var i = 0;
		var count = 0;
		do {
			if (ob[i].checked == true) {
				count++;
			}
			i++;
		} while (i < ob.length);


		if (count == 0) {
			alert("하나이상 선택해주세요.");
		} else {
			if (confirm("정말삭제하시겠습니까?")) {
				frm.submit();
			}
		}
	}

	//검색 기능 
	cdn_search() {
		if(search.word.value==""){
			alert("검색어를 입력하세요");
		}else{
			search.submit();
		}
	}

}