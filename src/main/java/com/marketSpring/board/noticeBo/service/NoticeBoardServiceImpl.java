package com.marketSpring.board.noticeBo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marketSpring.board.noticeBo.dao.NoticeBoardDAO;
import com.marketSpring.board.noticeBo.vo.NoticeBoardImgVO;
import com.marketSpring.board.noticeBo.vo.NoticeBoardVO;
import com.marketSpring.board.vo.ImageVO;

@Service("noticeBoardService")
@Transactional(propagation = Propagation.REQUIRED)
public class NoticeBoardServiceImpl implements NoticeBoardService {
	@Autowired
	private NoticeBoardDAO noticeBoardDAO;
	
	public Map listNoticeBoards(Map pagingMap) throws Exception {
		Map noticeBoardsMap = new HashMap();
		List<NoticeBoardVO> noticeBoardsList = noticeBoardDAO.selectAllNoticeBoards(pagingMap);
		int totNoticeBoards = noticeBoardDAO.selectTotNoticeBoards(pagingMap);
		
		noticeBoardsMap.put("noticeBoardsList", noticeBoardsList);
		noticeBoardsMap.put("totNoticeBoards", totNoticeBoards);
		
		return noticeBoardsMap;
	}
	  
	// 다중 이미지 추가하기
	@Override
	public int addNewNoticeBoard(Map noticeBoardMap) throws Exception {
		int noticeBoardNO = noticeBoardDAO.insertNewNoticeBoard(noticeBoardMap);
		noticeBoardMap.put("noticeBoardNO", noticeBoardNO);
		noticeBoardDAO.insertNewImg(noticeBoardMap);
		return noticeBoardNO;
	}

	// 다중 파일 보이기
	@Override
	public Map viewNoticeBoard(int noticeBoardNO) throws Exception {
		// 조회수 증가
		noticeBoardDAO.updateReadCnt(noticeBoardNO);
		Map noticeBoardMap = new HashMap();
		NoticeBoardVO noticeBoardVO = noticeBoardDAO.selectNoticeBoard(noticeBoardNO);
		List<NoticeBoardVO> imgFileList = noticeBoardDAO.selectImgFileList(noticeBoardNO);
		noticeBoardMap.put("noticeBoard", noticeBoardVO);
		noticeBoardMap.put("imgFileList", imgFileList);
		return noticeBoardMap;
	}
//	  
//	  
//	  // 단일 파일 보이기
//	  
//	  @Override public NoticeBoardVO viewNoticeBoard(int noticeBoardNO) throws Exception
//	  { NoticeBoardVO noticeBoardVO = noticeBoardDAO.selectNoticeBoard(noticeBoardNO); return
//	  noticeBoardVO; }

	@Override
	public void modNoticeBoard(Map noticeBoardMap) throws Exception {
		noticeBoardDAO.updateNoticeBoard(noticeBoardMap);
		List<NoticeBoardImgVO> fileList = (List<NoticeBoardImgVO>)noticeBoardMap.get("imgFileList");
		List<NoticeBoardImgVO> modAddimgFileList = (List<NoticeBoardImgVO>)noticeBoardMap.get("modAddimgFileList");

		if(fileList != null && fileList.size() != 0) {
//		if (fileList != null && !(fileList.get(0).getImg_filename().equals("")) ) {
			int added_img_num = Integer.parseInt((String)noticeBoardMap.get("added_img_num"));
			int pre_img_num = Integer.parseInt((String)noticeBoardMap.get("pre_img_num"));

			if(pre_img_num < added_img_num) {	//새로 추가된 이미지가 있는 경우와 없는 경우 나누어서 처리
				noticeBoardDAO.updateImgFile(noticeBoardMap);
				noticeBoardDAO.insertModNewImg(noticeBoardMap);
			} else {
				noticeBoardDAO.updateImgFile(noticeBoardMap);
			}
		} else if(modAddimgFileList != null && modAddimgFileList.size() != 0) {
			noticeBoardDAO.insertModNewImg(noticeBoardMap);
		}
		
	}

	@Override
	public void removeNoticeBoard(int noticeBoardNO) throws Exception {
		noticeBoardDAO.deleteNoticeBoard(noticeBoardNO);
	}

	@Override
	public void removeModImg(NoticeBoardImgVO noticeBoardImgVO) throws Exception {
		noticeBoardDAO.deleteModImg(noticeBoardImgVO);
	}
	 
}
