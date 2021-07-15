package com.marketSpring.board.qnaBoard.service;

import java.util.List;
import java.util.Map;

import com.marketSpring.board.qnaBoard.vo.qnaBoardImgVO;
import com.marketSpring.board.qnaBoard.vo.qnaBoardVO;


public interface qnaBoardService {
	
	public Map listqnaBoards(Map pagingMap) throws Exception;

	public int addNewqnaBoard(Map qnaBoardMap) throws Exception;
	
	public Map viewqnaBoard(int qnaBoardNO) throws Exception;
	
	public void modqnaBoard(Map qnaBoardMap) throws Exception;
	
	public void removeqnaBoard(int qnaBoardNO) throws Exception;

	public void removeModImg(qnaBoardImgVO qnaBoardImgVO) throws Exception;
	
}
