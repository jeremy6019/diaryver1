package com.jeremy94.diary.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeremy94.diary.dao.DiaryUserDAO;
import com.jeremy94.diary.domain.DiaryUser;

//bean을 자동 생성해주고 Service클래스라는 의미를 전달하기 위한 어노테이션 
@Service
public class DiaryUserServiceImpl implements DiaryUserService {

	@Autowired
	private DiaryUserDAO diaryUserDao;
	
	@Override
	public boolean emailCheck(String email) {
		boolean result = false; 
		//email은 존재하지 않아야 중복검사를 통과 
		String r = diaryUserDao.emailCheck(email);
		if( r == null) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean nicknameCheck(HttpServletRequest request) {
		boolean result = false; 
		//파라미터 읽기 
		String nickname = request.getParameter("nickname");
		
		String r = diaryUserDao.nicknameCheck(nickname);
		if( r == null) {
			result = true;
		}
		return result;
	}

	@Override
	public void join(MultipartHttpServletRequest request) {
		// 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");	
		String phone = request.getParameter("phone");
		
		// 라디오박스의 값 가져오기
		String gender = request.getParameter("gender");
		if("man".equals(gender)) {
			gender = "남자";
		}else {
			gender = "여자";
		}
		
		// 체크박스의 값 가져와서 하나의 문자열 만들기
		String [] hobbies = request.getParameterValues("hobby");
		// 각각의 문자열에 ,를 추가
		String hobby = "";
		for(String temp : hobbies) {
			hobby = hobby + temp + ",";
		}
		// 마지막 , 는 제거
		hobby = hobby.substring(0, hobby.length()-1);
		
		// 파일 이름 만들기와 업로드
		// file 파라미터의 값 가져오기
		MultipartFile f = request.getFile("image");
		// 원래 이름 가져오기
		String originName = f.getOriginalFilename();
		// 유일 무이한 이름 만들기
		String filename = email + originName;
		// 저장할 디렉토리 이름 만들기
		// 프로젝트 내의 경로
		// 실행하기 전에 프로젝트 내의 webapp(WebContext)안에 디렉토리를 생성
		String path = request.getServletContext().getRealPath("/userimage");
		// 업로드하는 파일이 있으면 저장하고 그렇지 않으면 default.png 사용
		if(originName.length() > 0) {
			// 파일 업로드
			File file = new File(path + "/" + filename);
			try {
				f.transferTo(file);
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}else {
			filename = "default.png";
		}
		
		// year, month, day 값을 가지고 java.sql.Date 만들기
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
			
		Calendar cal = new GregorianCalendar(Integer.parseInt(year), 
				Integer.parseInt(month)-1, Integer.parseInt(day)+1);
		Date birthday = new Date(cal.getTimeInMillis());

		// DAO 메소드 호출
		DiaryUser user = new DiaryUser();
		user.setEmail(email);
		// 암호화해서 저장하기
		user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
		user.setName(name);
		user.setNickname(nickname);
		user.setGender(gender);
		user.setImage(filename);
		user.setPhone(phone);
		user.setHobby(hobby);
		user.setBirthday(birthday);
		// 결과 리턴
		diaryUserDao.join(user);
	}

	@Override
	public boolean login(HttpServletRequest request) {
		boolean result = false;
		// 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		// email을 가지고 데이터 가져오기
		DiaryUser user = diaryUserDao.login(email);
		
		// 세션에서 로그인 정보를 가진 키의 값을 삭제
		// home.jsp 파일의 <c:if test="${user == null}"> 구문 때문에 Attribute에 user기입
		request.getSession().removeAttribute("user");
		
		// email에 해당하는 데이터가 존재한다면
		if(user != null) {
			// 비밀번호 비교
			if(BCrypt.checkpw(pw, user.getPw())) {
				// session의 user에 사용자 정보 저장
				user.setPw(null);
				request.getSession().setAttribute("user", user);
				// 로그인 성공
				result = true;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> address(HttpServletRequest request) {
		Map<String, Object>map = new HashMap<String, Object>();
		//파라미터를 읽어오기 
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		
		//파라미터 확인 - 다운로드 안될시 
	    System.out.println(longitude);
	    System.out.println(latitude);
	    
		//다운로드 받을 URL 생성 
	    String addr = 
	    		"https://dapi.kakao.com/v2/local/geo/coord2address.json?x="
	           +longitude+"&y="+latitude+"&input_coord=WGS84";
		//위의 주소에서 문자열을 다운로드 받기 
	    try {
		    URL url = new URL(addr);
		    HttpURLConnection con = 
		    		(HttpURLConnection)url.openConnection();
		    //옵션 설정 
		    con.setUseCaches(false);
		    con.setConnectTimeout(100000);
		    //헤더 설정 
		    con.addRequestProperty(
		    		"Authorization", "KakaoAK 9662a7b4d6113ff616ca34fa514f7e65");
		    //문자열 다운로드 
		    StringBuilder sb = new StringBuilder();
		    BufferedReader br = 
		    		new BufferedReader(new InputStreamReader(con.getInputStream()));
		    while(true) {
		    	String line = br.readLine();
		    	if(line == null) 
		    		break;
		    	sb.append(line + "\n");
		    }	
		    br.close();
		    con.disconnect();
		    
		    String json =sb.toString();
		    //json파싱 
		    JSONObject obj = new JSONObject(json);
		    JSONArray ar = obj.getJSONArray("documents");
		    //배열을 순회 
		    /*
		    for(int i=0; i<ar.length(); i + i+1) {
		    	JSONObject item = ar.getJSONObject(i);
		    }
		    */
		    //첫번째 데이터만 가져오기 
		    JSONObject item = ar.getJSONObject(0);
		    //road_address키의 값을 가져오기 
		    JSONObject road = item.getJSONObject("address");
		    String address = road.getString("address_name");
		    
		    //map에 저장 
		    map.put("address", address);
		    
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
		return map;
	}
	

}