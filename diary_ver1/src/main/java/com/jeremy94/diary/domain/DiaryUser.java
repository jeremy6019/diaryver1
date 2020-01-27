package com.jeremy94.diary.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class DiaryUser {
	private String email;
	private String pw;
	private String name;
	private String nickname;
	private String gender;
	private String image;
	private String phone;
	private String hobby;
	private Date birthday;

}


