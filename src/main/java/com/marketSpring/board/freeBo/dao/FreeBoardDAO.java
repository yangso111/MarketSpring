package com.marketSpring.board.freeBo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.marketSpring.board.freeBo.vo.FreeBoardImgVO;
import com.marketSpring.board.freeBo.vo.FreeBoardVO;


public interface FreeBoardDAO {

	public List selectAllFreeBoards(Map pagingMap) throws DataAccessException;
	public int selectTotFreeBoards(Map pagingMap) throws DataAccessException;

	public int insertNewFreeBoard(Map freeBoardMap) throws DataAccessException;
	public void insertNewImg(Map freeBoardMap) throws DataAccessException;
	
	public FreeBoardVO selectFreeBoard(int freeBoardNO) throws DataAccessException;
	public List selectImgFileList(int freeBoardNO) throws DataAccessException;
	public void updateReadCnt(int freeBoardNO) throws DataAccessException;
	
	public void updateFreeBoard(Map freeBoardMap) throws DataAccessException;
	
	public void updateImgFile(Map freeBoardMap) throws DataAccessException;
	public void insertModNewImg(Map freeBoardMap) throws DataAccessException;

	public void deleteModImg(FreeBoardImgVO freeBoardImgVO) throws DataAccessException;
	
	public void deleteFreeBoard(int freeBoardNO) throws DataAccessException;
	 
}
