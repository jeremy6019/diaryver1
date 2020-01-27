package com.jeremy94.diary.service;

import javax.servlet.http.HttpServletRequest;

import com.jeremy94.diary.domain.DiaryUser;

//모바일 서비스를 위한 인터페이스
public interface DiaryUserMobileService {
	// 로그인 처리를 위한 메소드
	public DiaryUser login(HttpServletRequest request);
}

