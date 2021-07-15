package com.marketSpring.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.marketSpring.member.vo.MemberVO;

public interface MemberDAO {
	public MemberVO login(Map loginMap) throws DataAccessException;
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	public String selectOverlappedID(String id) throws DataAccessException;
	
	// 주문 후 포인트 적립
	public void updateMemberPoint(Map  pointMap) throws DataAccessException;
	
}
