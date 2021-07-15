package com.marketSpring.board.noticeBo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.marketSpring.board.noticeBo.vo.NoticeBoardImgVO;
import com.marketSpring.board.noticeBo.vo.NoticeBoardVO;
import com.marketSpring.board.vo.ImageVO;


public interface NoticeBoardDAO {

	public List selectAllNoticeBoards(Map pagingMap) throws DataAccessException;
	public int selectTotNoticeBoards(Map pagingMap) throws DataAccessException;

	public int insertNewNoticeBoard(Map noticeBoardMap) throws DataAccessException;
	public void insertNewImg(Map noticeBoardMap) throws DataAccessException;
	
	public NoticeBoardVO selectNoticeBoard(int noticeBoardNO) throws DataAccessException;
	public List selectImgFileList(int noticeBoardNO) throws DataAccessException;
	public void updateReadCnt(int noticeBoardNO) throws DataAccessException;
	
	public void updateNoticeBoard(Map noticeBoardMap) throws DataAccessException;
	
	public void updateImgFile(Map noticeBoardMap) throws DataAccessException;
	public void insertModNewImg(Map noticeBoardMap) throws DataAccessException;

	public void deleteModImg(NoticeBoardImgVO noticeBoardImgVO) throws DataAccessException;
	
	public void deleteNoticeBoard(int noticeBoardNO) throws DataAccessException;
	 
}
