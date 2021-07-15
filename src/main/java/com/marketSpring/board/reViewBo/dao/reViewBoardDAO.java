package com.marketSpring.board.reViewBo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.marketSpring.board.reViewBo.vo.reViewBoardImgVO;
import com.marketSpring.board.reViewBo.vo.reViewBoardVO;
import com.marketSpring.board.vo.ImageVO;


public interface reViewBoardDAO {

	public List selectAllreViewBoards(Map pagingMap) throws DataAccessException;
	public int selectTotreViewBoards(Map pagingMap) throws DataAccessException;

	public int insertNewreViewBoard(Map reViewBoardMap) throws DataAccessException;
	public void insertNewImg(Map reViewBoardMap) throws DataAccessException;
	
	public reViewBoardVO selectreViewBoard(int reViewBoardNO) throws DataAccessException;
	public List selectImgFileList(int reViewBoardNO) throws DataAccessException;
	public void updateReadCnt(int reViewBoardNO) throws DataAccessException;
	
	public void updatereViewBoard(Map reViewBoardMap) throws DataAccessException;
	
	public void updateImgFile(Map reViewBoardMap) throws DataAccessException;
	public void insertModNewImg(Map reViewBoardMap) throws DataAccessException;

	public void deleteModImg(reViewBoardImgVO reViewBoardImgVO) throws DataAccessException;
	
	public void deletereViewBoard(int reViewBoardNO) throws DataAccessException;
	 
}
