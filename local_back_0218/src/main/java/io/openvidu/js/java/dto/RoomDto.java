package io.openvidu.js.java.dto;

public class RoomDto {
	private int session_id; // 수정해야함
	private String owner_id;
	private String title;
	private String theme;
	private String thumbnail;
	private String introduce;
	private String token;
	private String byegender;
	private int maleNum;
	private int femaleNum;
	private int rate;
	private int maxNum;


	public int getSession_id() {
		return session_id;
	}

	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getMaleNum() {
		return maleNum;
	}

	public void setMaleNum(int maleNum) {
		this.maleNum = maleNum;
	}

	public int getFemaleNum() {
		return femaleNum;
	}

	public void setFemaleNum(int femaleNum) {
		this.femaleNum = femaleNum;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

	public String getByegender() {
		return byegender;
	}

	public void setByegender(String byegender) {
		this.byegender = byegender;
	}

	
	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	@Override
	public String toString() {
		return "RoomDto [session_id=" + session_id + ", owner_id=" + owner_id + ", title=" + title + ", theme=" + theme
				+ ", thumbnail=" + thumbnail + ", introduce=" + introduce + ", token=" + token + ", byegender="
				+ byegender + ", maleNum=" + maleNum + ", femaleNum=" + femaleNum + ", rate=" + rate + ", maxNum="
				+ maxNum + "]";
	}
	
}