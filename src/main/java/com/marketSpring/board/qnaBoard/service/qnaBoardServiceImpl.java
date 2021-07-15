package com.marketSpring.board.qnaBoard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marketSpring.board.qnaBoard.vo.qnaBoardImgVO;
import com.marketSpring.board.qnaBoard.dao.qnaBoardDAO;
import com.marketSpring.board.qnaBoard.vo.qnaBoardVO;


@Service("qnaBoardService")
@Transactional(propagation = Propagation.REQUIRED)
public class qnaBoardServiceImpl implements qnaBoardService {
	@Autowired
	private qnaBoardDAO qnaBoardDAO;
	
	public Map listqnaBoards(Map pagingMap) throws Exception {
		Map qnaBoardsMap = new HashMap();
		List<qnaBoardVO> qnaBoardsList = qnaBoardDAO.selectAllqnaBoards(pagingMap);
		int totqnaBoards = qnaBoardDAO.selectTotqnaBoards(pagingMap);
		
		qnaBoardsMap.put("qnaBoardsList", qnaBoardsList);
		qnaBoardsMap.put("totqnaBoards", totqnaBoards);
		
		return qnaBoardsMap;
	}
	  
	// 다중 이미지 추가하기
	@Override
	public int addNewqnaBoard(Map qnaBoardMap) throws Exception {
		int qnaBoardNO = qnaBoardDAO.insertNewqnaBoard(qnaBoardMap);
		qnaBoardMap.put("qnaBoardNO", qnaBoardNO);
		qnaBoardDAO.insertNewImg(qnaBoardMap);
		return qnaBoardNO;
	}

	// 다중 파일 보이기
	@Override
	public Map viewqnaBoard(int qnaBoardNO) throws Exception {
		// 조회수 증가
		qnaBoardDAO.updateReadCnt(qnaBoardNO);
		Map qnaBoardMap = new HashMap();
		qnaBoardVO qnaBoardVO = qnaBoardDAO.selectqnaBoard(qnaBoardNO);
		List<qnaBoardVO> imgFileList = qnaBoardDAO.selectImgFileList(qnaBoardNO);
		qnaBoardMap.put("qnaBoard", qnaBoardVO);
		qnaBoardMap.put("imgFileList", imgFileList);
		return qnaBoardMap;
	}
//	  
//	  
//	  // 단일 파일 보이기
//	  
//	  @Override public FreeBoardVO viewFreeBoard(int freeBoardNO) throws Exception
//	  { FreeBoardVO freeBoardVO = freeBoardDAO.selectFreeBoard(freeBoardNO); return
//	  freeBoardVO; }

	@Override
	public void modqnaBoard(Map qnaBoardMap) throws Exception {
		qnaBoardDAO.updateqnaBoard(qnaBoardMap);
		List<qnaBoardImgVO> fileList = (List<qnaBoardImgVO>)qnaBoardMap.get("imgFileList");
		List<qnaBoardImgVO> modAddimgFileList = (List<qnaBoardImgVO>)qnaBoardMap.get("modAddimgFileList");

		if(fileList != null && fileList.size() != 0) {
//		if (fileList != null && !(fileList.get(0).getImg_filename().equals("")) ) {
			int added_img_num = Integer.parseInt((String)qnaBoardMap.get("added_img_num"));
			int pre_img_num = Integer.parseInt((String)qnaBoardMap.get("pre_img_num"));

			if(pre_img_num < added_img_num) {	//새로 추가된 이미지가 있는 경우와 없는 경우 나누어서 처리
				qnaBoardDAO.updateImgFile(qnaBoardMap);
				qnaBoardDAO.insertModNewImg(qnaBoardMap);
			} else {
				qnaBoardDAO.updateImgFile(qnaBoardMap);
			}
		} else if(modAddimgFileList != null && modAddimgFileList.size() != 0) {
			qnaBoardDAO.insertModNewImg(qnaBoardMap);
		}
		
	}

	@Override
	public void removeqnaBoard(int qnaBoardNO) throws Exception {
		qnaBoardDAO.deleteqnaBoard(qnaBoardNO);
	}

	@Override
	public void removeModImg(qnaBoardImgVO qnaBoardImgVO) throws Exception {
		qnaBoardDAO.deleteModImg(qnaBoardImgVO);
	}
	 
}
