package com.marketSpring.member.service;

import java.util.Map;

import com.marketSpring.member.vo.MemberVO;

public interface MemberService {
	public MemberVO login(Map  loginMap) throws Exception;
	public void addMember(MemberVO memberVO) throws Exception;
	public String overlapped(String id) throws Exception;

	public void addPoint(Map  pointMap) throws Exception;
	
}
