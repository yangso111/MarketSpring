package com.marketSpring.board.freeBo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marketSpring.board.freeBo.dao.FreeBoardDAO;
import com.marketSpring.board.freeBo.vo.FreeBoardImgVO;
import com.marketSpring.board.freeBo.vo.FreeBoardVO;

@Service("freeBoardService")
@Transactional(propagation = Propagation.REQUIRED)
public class FreeBoardServiceImpl implements FreeBoardService {
	@Autowired
	private FreeBoardDAO freeBoardDAO;
	
	public Map listFreeBoards(Map pagingMap) throws Exception {
		Map freeBoardsMap = new HashMap();
		List<FreeBoardVO> freeBoardsList = freeBoardDAO.selectAllFreeBoards(pagingMap);
		int totFreeBoards = freeBoardDAO.selectTotFreeBoards(pagingMap);
		
		freeBoardsMap.put("freeBoardsList", freeBoardsList);
		freeBoardsMap.put("totFreeBoards", totFreeBoards);
		
		return freeBoardsMap;
	}
	  
	// 다중 이미지 추가하기
	@Override
	public int addNewFreeBoard(Map freeBoardMap) throws Exception {
		int freeBoardNO = freeBoardDAO.insertNewFreeBoard(freeBoardMap);
		freeBoardMap.put("freeBoardNO", freeBoardNO);
		freeBoardDAO.insertNewImg(freeBoardMap);
		return freeBoardNO;
	}

	// 다중 파일 보이기
	@Override
	public Map viewFreeBoard(int freeBoardNO) throws Exception {
		// 조회수 증가
		freeBoardDAO.updateReadCnt(freeBoardNO);
		Map freeBoardMap = new HashMap();
		FreeBoardVO freeBoardVO = freeBoardDAO.selectFreeBoard(freeBoardNO);
		List<FreeBoardVO> imgFileList = freeBoardDAO.selectImgFileList(freeBoardNO);
		freeBoardMap.put("freeBoard", freeBoardVO);
		freeBoardMap.put("imgFileList", imgFileList);
		return freeBoardMap;
	}
//	  
//	  
//	  // 단일 파일 보이기
//	  
//	  @Override public FreeBoardVO viewFreeBoard(int freeBoardNO) throws Exception
//	  { FreeBoardVO freeBoardVO = freeBoardDAO.selectFreeBoard(freeBoardNO); return
//	  freeBoardVO; }

	@Override
	public void modFreeBoard(Map freeBoardMap) throws Exception {
		freeBoardDAO.updateFreeBoard(freeBoardMap);
		List<FreeBoardImgVO> fileList = (List<FreeBoardImgVO>)freeBoardMap.get("imgFileList");
		List<FreeBoardImgVO> modAddimgFileList = (List<FreeBoardImgVO>)freeBoardMap.get("modAddimgFileList");

		if(fileList != null && fileList.size() != 0) {
//		if (fileList != null && !(fileList.get(0).getImg_filename().equals("")) ) {
			int added_img_num = Integer.parseInt((String)freeBoardMap.get("added_img_num"));
			int pre_img_num = Integer.parseInt((String)freeBoardMap.get("pre_img_num"));

			if(pre_img_num < added_img_num) {	//새로 추가된 이미지가 있는 경우와 없는 경우 나누어서 처리
				freeBoardDAO.updateImgFile(freeBoardMap);
				freeBoardDAO.insertModNewImg(freeBoardMap);
			} else {
				freeBoardDAO.updateImgFile(freeBoardMap);
			}
		} else if(modAddimgFileList != null && modAddimgFileList.size() != 0) {
			freeBoardDAO.insertModNewImg(freeBoardMap);
		}
		
	}

	@Override
	public void removeFreeBoard(int freeBoardNO) throws Exception {
		freeBoardDAO.deleteFreeBoard(freeBoardNO);
	}

	@Override
	public void removeModImg(FreeBoardImgVO freeBoardImgVO) throws Exception {
		freeBoardDAO.deleteModImg(freeBoardImgVO);
	}
	 
}
