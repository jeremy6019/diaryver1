package com.jeremy94.diary;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeremy94.diary.domain.DiaryUser;
import com.jeremy94.diary.service.DiaryUserMobileService;
import com.jeremy94.diary.service.DiaryUserService;

@RestController
public class JSONController {
		@Autowired
		private DiaryUserService diaryuserService;
		
		//이메일 중복검사를 위한 메소드 
		@RequestMapping(value="user/emailcheck", method = RequestMethod.GET)
		//email이라는 파라미터를 email 이라는 문자열 변수에 저장 
		public Map<String, Object> emailCheck(@RequestParam("email") String email) {		
			//서비스의 메소드 호출 
			boolean result = diaryuserService.emailCheck(email);
			//결과 만들기 
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("result",  result + "");
			//존재하는 email이면 result에 "false"  없는 email이면 
			//result에 true가 저장됩니다. 
			return map;
		}
		
		// nicknamecheck 요청을 처리할 메소드 
		// 파라미터를 Service에 읽도록 했기 때문에 매개변수가 
		// HttpServletRequest 
		@RequestMapping(value="user/nicknamecheck", method = RequestMethod.GET)
		public Map<String, Object> nicknameCheck(HttpServletRequest request) {		
			//서비스의 메소드 호출 
			boolean result = diaryuserService.nicknameCheck(request);
			//결과 만들기 
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("result",  result + "");

			return map;
		}
		
		@Autowired
		private DiaryUserMobileService diaryUserMobileService;
		@RequestMapping(value="user/mobilelogin", method=RequestMethod.GET)
		public Map<String, Object> mobileLogin(HttpServletRequest request){
			// 리턴할 Map 만들기
			Map<String, Object> map = new HashMap<String, Object>();
			// 서비스 메소드 호출
			DiaryUser user = diaryUserMobileService.login(request);
			if(user == null) {
				map.put("result", "fail");
			}else {
				map.put("result", "success");
				map.put("user", user);
			}
			return map;
		}

		//주소를 리턴하는 요청처리 메소드 
		@RequestMapping(value="address", method=RequestMethod.GET)
		public Map<String, Object>address(HttpServletRequest request){
			return diaryuserService.address(request);
		}
		
		
}
