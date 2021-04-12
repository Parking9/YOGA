package io.openvidu.js.java.dto;

import java.util.Arrays;

public class MemberDto {
	private int id;
	private int manager;
	private String userid;
	private String username;
	private String userpwd;
	private String userpwdconfirm;
	private String email;
	private String gender;
	private String ages;
	private String joindate;
	private String profileImg;
	private String introduce;
	private int session_id;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAges() {
		return ages;
	}

	public void setAges(String ages) {
		this.ages = ages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUserpwdconfirm() {
		return userpwdconfirm;
	}

	public void setUserpwdconfirm(String userpwdconfirm) {
		this.userpwdconfirm = userpwdconfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public int getSession_id() {
		return session_id;
	}

	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", manager=" + manager + ", userid=" + userid + ", username=" + username
				+ ", userpwd=" + userpwd + ", userpwdconfirm=" + userpwdconfirm + ", email=" + email + ", gender="
				+ gender + ", ages=" + ages + ", joindate=" + joindate + ", profileImg=" + profileImg
				+ ", introduce=" + introduce + ", session_id=" + session_id + "]";
	}

}