package com.jeremy94.diary.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jeremy94.diary.domain.DiaryUser;

//bean을 자동 생성해주는 어노테이션 
//데이터베이스 작업을 해주는 클래스라는 의미를 전달하기 위해서 
//@Component 대신에 @Repository라고 작성 
@Repository
public class DiaryUserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	//email 중복검사를 위한 메소드 
	public String emailCheck(String email) {
		return sqlSession.selectOne(
				"diaryuser.emailcheck", email);
	}
	
	//nickname 중복검사를 위한 메소드 
	public String nicknameCheck(String nickname) {
		return sqlSession.selectOne(
				"diaryuser.nicknamecheck", nickname);
	}
	
	// 회원 가입을 위한 메소드
	public int join(DiaryUser diaryUser) {
		return sqlSession.insert("diaryuser.join", diaryUser);
	}
	
	// 로그인 처리를 위한 메소드
	public DiaryUser login(String email) {
		return sqlSession.selectOne("diaryuser.login", email);
	}

	
}

