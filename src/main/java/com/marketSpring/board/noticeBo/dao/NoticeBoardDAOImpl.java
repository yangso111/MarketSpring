package com.marketSpring.board.noticeBo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.marketSpring.board.noticeBo.vo.NoticeBoardImgVO;
import com.marketSpring.board.noticeBo.vo.NoticeBoardVO;
import com.marketSpring.board.vo.ImageVO;


@Repository("noticeBoardDAO")
public class NoticeBoardDAOImpl implements NoticeBoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	String ns = "mapper.board.noticeBo.";

	@Override
	public List selectAllNoticeBoards(Map pagingMap) throws DataAccessException {
		List<NoticeBoardVO> noticeBoardsList = sqlSession.selectList(ns + "selectAllNoticeBoardsList", pagingMap);
		return noticeBoardsList;
	}

	@Override
	public int selectTotNoticeBoards(Map pagingMap) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectTotNoticeBoards", pagingMap);
	}

	@Override
	public int insertNewNoticeBoard(Map noticeBoardMap) throws DataAccessException {
		int noticeBoardNO = selectNewNoticeBoardNO();
		noticeBoardMap.put("noticeBoardNO", noticeBoardNO);
		sqlSession.insert(ns + "insertNewNoticeBoard", noticeBoardMap);
		return noticeBoardNO;
	}

	// 다중 파일 업로드
	@Override
	public void insertNewImg(Map noticeBoardMap) throws DataAccessException {
		List<NoticeBoardImgVO> imgFileList = (ArrayList) noticeBoardMap.get("imgFileList");
		int noticeBoardNO = (Integer) noticeBoardMap.get("noticeBoardNO");
		int imgFileNO = selectNewImgFileNO();
		
		// 이미지가 있는 경우만 DB에 insert 
		if (imgFileList.get(0).getImg_filename() != null && !(imgFileList.get(0).getImg_filename().equals("")) ) {
//			System.out.println("이미지 있음");
//			System.out.println("파일명: " + imgFileList.get(0).getImg_filename());
			for (NoticeBoardImgVO noticeBoardImgVO : imgFileList) {
				noticeBoardImgVO.setNotice_bo_img_no(++imgFileNO);
				noticeBoardImgVO.setNotice_bo_no(noticeBoardNO);
			}
			sqlSession.insert(ns + "insertNewImg", imgFileList);
		}
	}

	@Override
	public NoticeBoardVO selectNoticeBoard(int noticeBoardNO) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNoticeBoard", noticeBoardNO);
	}
	
	@Override
	public List selectImgFileList(int noticeBoardNO) throws DataAccessException {
		List<NoticeBoardImgVO> imgFileList = null;
		imgFileList = sqlSession.selectList(ns + "selectImgFileList", noticeBoardNO);
		return imgFileList;
	}

	@Override
	public void updateNoticeBoard(Map noticeBoardMap) throws DataAccessException {
		sqlSession.update(ns + "updateNoticeBoard", noticeBoardMap);
	}

	@Override
	public void deleteNoticeBoard(int noticeBoardNO) throws DataAccessException {
		sqlSession.update(ns + "deleteNoticeBoard", noticeBoardNO);
		// 게시글 이미지 삭제
		sqlSession.update(ns + "deleteNoticeBoardImg", noticeBoardNO);

	}

	private int selectNewNoticeBoardNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewNoticeBoardNO");
	}

	private int selectNewImgFileNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewImgFileNO");
	}

	@Override
	public void updateReadCnt(int noticeBoardNO) throws DataAccessException {
		sqlSession.update(ns + "updateReadCnt", noticeBoardNO);
	}

	@Override
	public void updateImgFile(Map noticeBoardMap) throws DataAccessException {
		List<NoticeBoardImgVO> imgFileList = (ArrayList) noticeBoardMap.get("imgFileList");
		int noticeBoardNO = Integer.parseInt((String) noticeBoardMap.get("noticeBoardNO"));
		
		for (int i = imgFileList.size() - 1; i >= 0; i--) {
			NoticeBoardImgVO noticeBoardImgVO = imgFileList.get(i);
			String imgFileName = noticeBoardImgVO.getImg_filename();
			if (imgFileName == null) { // 기존에 이미지를 수정하지 않는 경우 파일명이 null 이므로 수정할 필요가 없다.
				imgFileList.remove(i);
			} else {
				noticeBoardImgVO.setNotice_bo_no(noticeBoardNO);
			}
		}

		if (imgFileList != null && imgFileList.size() != 0) {
			sqlSession.update(ns + "updateImageFile", imgFileList);
		}

	}

	@Override
	public void insertModNewImg(Map noticeBoardMap) throws DataAccessException {
		List<NoticeBoardImgVO> modAddimgFileList = (ArrayList<NoticeBoardImgVO>) noticeBoardMap.get("modAddimgFileList");
		int noticeBoardNO = Integer.parseInt((String) noticeBoardMap.get("noticeBoardNO"));

		int imgFileNO = selectNewImgFileNO();

		for (NoticeBoardImgVO noticeBoardImgVO : modAddimgFileList) {
			noticeBoardImgVO.setNotice_bo_no(noticeBoardNO);
			noticeBoardImgVO.setNotice_bo_img_no(++imgFileNO);
		}

		sqlSession.insert(ns + "insertModNewImg", modAddimgFileList);
		
	}

	@Override
	public void deleteModImg(NoticeBoardImgVO noticeBoardImgVO) throws DataAccessException {
		sqlSession.delete(ns + "deleteModImg", noticeBoardImgVO );		
	}

}
