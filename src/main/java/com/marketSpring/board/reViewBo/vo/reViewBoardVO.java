package com.marketSpring.board.reViewBo.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("reViewBoardVO")
public class reViewBoardVO {
	private int level;
	private int reView_bo_no;
	private int parent_no;
	private String title;
	private String content;
	private int read_count;
	private String mem_id;
	private Date write_date;
	private int is_del;
	
	
	public reViewBoardVO() {
		System.out.println("reViewBoardVO 생성자");
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getreView_bo_no() {
		return reView_bo_no;
	}
	public void setreView_bo_no(int reView_bo_no) {
		this.reView_bo_no = reView_bo_no;
	}
	public int getParent_no() {
		return parent_no;
	}
	public void setParent_no(int parent_no) {
		this.parent_no = parent_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	
}
