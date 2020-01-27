package com.jeremy94.diary.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremy94.diary.dao.DiaryBoardDAO;
import com.jeremy94.diary.domain.DiaryBoard;
import com.jeremy94.diary.domain.DiaryUser;

@Service
public class DiaryBoardServiceImpl implements DiaryBoardService {
	@Autowired
	private DiaryBoardDAO diaryBoardDao;

	@Override
	public boolean write(HttpServletRequest request) {
		boolean result = false;
		
		// 필요한 파라미터 읽기
		String boardtitle = request.getParameter("title");
		String boardcontent = request.getParameter("content");
		
		// 로그인 한 유저의 이메일 가져오기
		HttpSession session = request.getSession();
		DiaryUser user = (DiaryUser)session.getAttribute("user");
		String email = user.getEmail();
		
		// 접속한 클라이언트의 ip 가져오기
		String ip = request.getRemoteAddr();
		
		// 글번호 만들기 - 가장 큰 글번호 + 1
		int boardnum = 1;
		Integer num = diaryBoardDao.maxNum();
		if(num != null) {
			boardnum = num + 1;
		}
		
		Calendar cal = new GregorianCalendar();
		Date boarddate = new Date(cal.getTimeInMillis());
		Date updatedate = new Date(cal.getTimeInMillis());
		
		// 게시글 삽입을 위한 DTO 만들기
		DiaryBoard board = new DiaryBoard();
		board.setBoardnum(boardnum);
		board.setBoardtitle(boardtitle);
		board.setBoardcontent(boardcontent);
		board.setBoarddate(boarddate);
		board.setBoardip(ip);
		board.setUpdatedate(updatedate);
		board.setEmail(email);
		
		// DAO 메소드 호출
		int r = diaryBoardDao.write(board);
		// 결과 생성
		if(r>0) {
			result = true;
		}
		return result;
	}

	@Override
	public List<DiaryBoard> list(HttpServletRequest request){
		List<DiaryBoard> list = diaryBoardDao.list();
		// 오늘 날짜 생성
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String t = sdf.format(today);
		for(DiaryBoard board : list) {
			// 오늘 날짜와 작성일이 같을 때는 시간을 출력
			if(t.equals(sdf.format(board.getUpdatedate()))) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
				board.setDispdate(sdf1.format(board.getUpdatedate()));
			}else {
				board.setDispdate(sdf.format(board.getUpdatedate()));
			}
		}
		return list;
	}

}
