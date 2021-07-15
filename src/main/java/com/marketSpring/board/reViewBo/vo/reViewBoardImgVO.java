package com.marketSpring.board.reViewBo.vo;

import java.sql.Date;

public class reViewBoardImgVO {
	private int reView_bo_img_no;
	private int reView_bo_no;
	private String img_filename;
	private Date reg_date;
	private int is_del;
	
	public int getreView_bo_img_no() {
		return reView_bo_img_no;
	}
	public void setreView_bo_img_no(int reView_bo_img_no) {
		this.reView_bo_img_no = reView_bo_img_no;
	}
	public int getreView_bo_no() {
		return reView_bo_no;
	}
	public void setreView_bo_no(int reView_bo_no) {
		this.reView_bo_no = reView_bo_no;
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
