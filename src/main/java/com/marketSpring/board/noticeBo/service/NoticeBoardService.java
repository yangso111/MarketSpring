package com.marketSpring.board.noticeBo.service;

import java.util.List;
import java.util.Map;

import com.marketSpring.board.noticeBo.vo.NoticeBoardImgVO;
import com.marketSpring.board.noticeBo.vo.NoticeBoardVO;
import com.marketSpring.board.vo.ImageVO;

public interface NoticeBoardService {
	
	public Map listNoticeBoards(Map pagingMap) throws Exception;

	public int addNewNoticeBoard(Map noticeBoardMap) throws Exception;
	
	public Map viewNoticeBoard(int noticeBoardNO) throws Exception;
	
	public void modNoticeBoard(Map noticeBoardMap) throws Exception;
	public void removeNoticeBoard(int noticeBoardNO) throws Exception;

	public void removeModImg(NoticeBoardImgVO noticeBoardImgVO) throws Exception;
	
}
