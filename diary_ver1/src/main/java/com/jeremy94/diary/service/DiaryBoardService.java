package com.jeremy94.diary.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jeremy94.diary.domain.DiaryBoard;

public interface DiaryBoardService {
	// 게시글 작성을 처리하기 위한 메소드
	public boolean write(HttpServletRequest request);

	// 게시글 작성된 목록을 보기위한 메소드
	public List<DiaryBoard> list(HttpServletRequest request);


}
