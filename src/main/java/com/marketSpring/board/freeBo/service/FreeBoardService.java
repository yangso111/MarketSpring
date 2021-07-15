package com.marketSpring.board.freeBo.service;

import java.util.Map;

import com.marketSpring.board.freeBo.vo.FreeBoardImgVO;

public interface FreeBoardService {
	
	public Map listFreeBoards(Map pagingMap) throws Exception;

	public int addNewFreeBoard(Map freeBoardMap) throws Exception;
	
	public Map viewFreeBoard(int freeBoardNO) throws Exception;
	
	public void modFreeBoard(Map freeBoardMap) throws Exception;
	public void removeFreeBoard(int freeBoardNO) throws Exception;

	public void removeModImg(FreeBoardImgVO freeBoardImgVO) throws Exception;
	
}
