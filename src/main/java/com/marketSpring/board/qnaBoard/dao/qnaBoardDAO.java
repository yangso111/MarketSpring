package com.marketSpring.board.qnaBoard.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.marketSpring.board.qnaBoard.vo.qnaBoardImgVO;
import com.marketSpring.board.qnaBoard.vo.qnaBoardVO;


public interface qnaBoardDAO {

	public List selectAllqnaBoards(Map pagingMap) throws DataAccessException;
	public int selectTotqnaBoards(Map pagingMap) throws DataAccessException;

	public int insertNewqnaBoard(Map qnaBoardMap) throws DataAccessException;
	public void insertNewImg(Map qnaBoardMap) throws DataAccessException;
	
	public qnaBoardVO selectqnaBoard(int qnaBoardNO) throws DataAccessException;
	public List selectImgFileList(int qnaBoardNO) throws DataAccessException;
	public void updateReadCnt(int qnaBoardNO) throws DataAccessException;
	
	public void updateqnaBoard(Map qnaBoardMap) throws DataAccessException;
	
	public void updateImgFile(Map qnaBoardMap) throws DataAccessException;
	public void insertModNewImg(Map qnaBoardMap) throws DataAccessException;

	public void deleteModImg(qnaBoardImgVO qnaBoardImgVO) throws DataAccessException;
	
	public void deleteqnaBoard(int qnaBoardNO) throws DataAccessException;
	 
}
