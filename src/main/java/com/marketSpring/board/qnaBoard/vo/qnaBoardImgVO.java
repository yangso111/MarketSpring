package com.marketSpring.board.qnaBoard.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

public class qnaBoardImgVO {
	private int qna_bo_img_no;
	private int qna_bo_no;
	private String img_filename;
	private Date reg_date;
	private int is_del;
	
	
	public int getQna_bo_img_no() {
		return qna_bo_img_no;
	}
	public void setQna_bo_img_no(int qna_bo_img_no) {
		this.qna_bo_img_no = qna_bo_img_no;
	}
	public int getQna_bo_no() {
		return qna_bo_no;
	}
	public void setQna_bo_no(int qna_bo_no) {
		this.qna_bo_no = qna_bo_no;
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
