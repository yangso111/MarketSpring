package com.marketSpring.board.reViewBo.service;

import java.util.List;
import java.util.Map;

import com.marketSpring.board.reViewBo.vo.reViewBoardImgVO;
import com.marketSpring.board.reViewBo.vo.reViewBoardVO;
import com.marketSpring.board.vo.ImageVO;

public interface reViewBoardService {
	
	public Map listreViewBoards(Map pagingMap) throws Exception;

	public int addNewreViewBoard(Map reViewBoardMap) throws Exception;
	
	public Map viewreViewBoard(int reViewBoardNO) throws Exception;
	
	public void modreViewBoard(Map reViewBoardMap) throws Exception;
	public void removereViewBoard(int reViewBoardNO) throws Exception;

	public void removeModImg(reViewBoardImgVO reViewBoardImgVO) throws Exception;
	
}
