package com.marketSpring.board.reViewBo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marketSpring.board.reViewBo.dao.reViewBoardDAO;
import com.marketSpring.board.reViewBo.vo.reViewBoardImgVO;
import com.marketSpring.board.reViewBo.vo.reViewBoardVO;
import com.marketSpring.board.vo.ImageVO;

@Service("reViewBoardService")
@Transactional(propagation = Propagation.REQUIRED)
public class reViewBoardServiceImpl implements reViewBoardService {
	@Autowired
	private reViewBoardDAO reViewBoardDAO;
	
	public Map listreViewBoards(Map pagingMap) throws Exception {
		Map reViewBoardsMap = new HashMap();
		List<reViewBoardVO> reViewBoardsList = reViewBoardDAO.selectAllreViewBoards(pagingMap);
		int totreViewBoards = reViewBoardDAO.selectTotreViewBoards(pagingMap);
		
		reViewBoardsMap.put("reViewBoardsList", reViewBoardsList);
		reViewBoardsMap.put("totreViewBoards", totreViewBoards);
		
		return reViewBoardsMap;
	}
	  
	// 다중 이미지 추가하기
	@Override
	public int addNewreViewBoard(Map reViewBoardMap) throws Exception {
		int reViewBoardNO = reViewBoardDAO.insertNewreViewBoard(reViewBoardMap);
		reViewBoardMap.put("reViewBoardNO", reViewBoardNO);
		reViewBoardDAO.insertNewImg(reViewBoardMap);
		return reViewBoardNO;
	}

	// 다중 파일 보이기
	@Override
	public Map viewreViewBoard(int reViewBoardNO) throws Exception {
		// 조회수 증가
		reViewBoardDAO.updateReadCnt(reViewBoardNO);
		Map reViewBoardMap = new HashMap();
		reViewBoardVO reViewBoardVO = reViewBoardDAO.selectreViewBoard(reViewBoardNO);
		List<reViewBoardVO> imgFileList = reViewBoardDAO.selectImgFileList(reViewBoardNO);
		reViewBoardMap.put("reViewBoard", reViewBoardVO);
		reViewBoardMap.put("imgFileList", imgFileList);
		return reViewBoardMap;
	}
//	  
//	  
//	  // 단일 파일 보이기
//	  
//	  @Override public reViewBoardVO viewreViewBoard(int reViewBoardNO) throws Exception
//	  { reViewBoardVO reViewBoardVO = reViewBoardDAO.selectreViewBoard(reViewBoardNO); return
//	  reViewBoardVO; }

	@Override
	public void modreViewBoard(Map reViewBoardMap) throws Exception {
		reViewBoardDAO.updatereViewBoard(reViewBoardMap);
		List<reViewBoardImgVO> fileList = (List<reViewBoardImgVO>)reViewBoardMap.get("imgFileList");
		List<reViewBoardImgVO> modAddimgFileList = (List<reViewBoardImgVO>)reViewBoardMap.get("modAddimgFileList");

		if(fileList != null && fileList.size() != 0) {
//		if (fileList != null && !(fileList.get(0).getImg_filename().equals("")) ) {
			int added_img_num = Integer.parseInt((String)reViewBoardMap.get("added_img_num"));
			int pre_img_num = Integer.parseInt((String)reViewBoardMap.get("pre_img_num"));

			if(pre_img_num < added_img_num) {	//새로 추가된 이미지가 있는 경우와 없는 경우 나누어서 처리
				reViewBoardDAO.updateImgFile(reViewBoardMap);
				reViewBoardDAO.insertModNewImg(reViewBoardMap);
			} else {
				reViewBoardDAO.updateImgFile(reViewBoardMap);
			}
		} else if(modAddimgFileList != null && modAddimgFileList.size() != 0) {
			reViewBoardDAO.insertModNewImg(reViewBoardMap);
		}
		
	}

	@Override
	public void removereViewBoard(int reViewBoardNO) throws Exception {
		reViewBoardDAO.deletereViewBoard(reViewBoardNO);
	}

	@Override
	public void removeModImg(reViewBoardImgVO reViewBoardImgVO) throws Exception {
		reViewBoardDAO.deleteModImg(reViewBoardImgVO);
	}
	 
}
