package com.jeremy94.diary.service;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremy94.diary.dao.DiaryUserDAO;
import com.jeremy94.diary.domain.DiaryUser;

@Service
public class DiaryUserMobileServiceImpl implements DiaryUserMobileService {
	@Autowired
	private DiaryUserDAO diaryUserDao;
	
	@Override
	public DiaryUser login(HttpServletRequest request) {
		// 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		// email을 이용해서 데이터 찾아오기
		DiaryUser user = diaryUserDao.login(email);
		if(user != null) {
			// 로그인 성공한 경우는 user를 리턴
			if(BCrypt.checkpw(pw, user.getPw())) {
				user.setPw(null);
				return user;
			}
		}
		// 로그인 실패한 경우에는 null을 리턴
		return null;
	}
}



