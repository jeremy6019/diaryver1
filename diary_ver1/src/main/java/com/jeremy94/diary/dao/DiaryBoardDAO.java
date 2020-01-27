package com.jeremy94.diary.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jeremy94.diary.domain.DiaryBoard;

@Repository
public class DiaryBoardDAO {
	@Autowired 
	// xml 을 이용한 MyBatis 사용
	private SqlSession sqlSession;
	// 인터페이스 이용한 MyBaits 사용할 때는 위의 구문 생략
	// 하이버네이트를 사용할 때는 변경
	// private SessionFactory sessionFactory
	
	// 가장 큰 글번호를 찾아오는 SQL 실행
	public Integer maxNum() {
		return sqlSession.selectOne("diaryboard.maxnum");
	}

	// 글쓰기를 위한 SQL 실행
	public int write(DiaryBoard board) {
		return sqlSession.insert("diaryboard.write", board);
	}
	
	// 목록보기를 위한 SQL 실행
	public List<DiaryBoard> list(){
		return sqlSession.selectList("diaryboard.list");
	}

	
}
