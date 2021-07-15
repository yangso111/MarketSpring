package com.marketSpring.board.noticeBo.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

public class NoticeBoardImgVO {
	private int notice_bo_img_no;
	private int notice_bo_no;
	private String img_filename;
	private Date reg_date;
	private int is_del;
	
	public int getNotice_bo_img_no() {
		return notice_bo_img_no;
	}
	public void setNotice_bo_img_no(int notice_bo_img_no) {
		this.notice_bo_img_no = notice_bo_img_no;
	}
	public int getNotice_bo_no() {
		return notice_bo_no;
	}
	public void setNotice_bo_no(int notice_bo_no) {
		this.notice_bo_no = notice_bo_no;
	}
	public String getImg_filename() {
		return img_filename;
	}
	public void setImg_filename(String img_filename) {
		this.img_filename = img_filename;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	
}
