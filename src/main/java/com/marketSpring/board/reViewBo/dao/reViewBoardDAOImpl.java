package com.marketSpring.board.reViewBo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.marketSpring.board.reViewBo.vo.reViewBoardImgVO;
import com.marketSpring.board.reViewBo.vo.reViewBoardVO;
import com.marketSpring.board.vo.ImageVO;


@Repository("reViewBoardDAO")
public class reViewBoardDAOImpl implements reViewBoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	String ns = "mapper.board.reViewBo.";

	@Override
	public List selectAllreViewBoards(Map pagingMap) throws DataAccessException {
		List<reViewBoardVO> reViewBoardsList = sqlSession.selectList(ns + "selectAllreViewBoardsList", pagingMap);
		return reViewBoardsList;
	}

	@Override
	public int selectTotreViewBoards(Map pagingMap) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectTotreViewBoards", pagingMap);
	}

	@Override
	public int insertNewreViewBoard(Map reViewBoardMap) throws DataAccessException {
		int reViewBoardNO = selectNewreViewBoardNO();
		reViewBoardMap.put("reViewBoardNO", reViewBoardNO);
		sqlSession.insert(ns + "insertNewreViewBoard", reViewBoardMap);
		return reViewBoardNO;
	}

	// 다중 파일 업로드
	@Override
	public void insertNewImg(Map reViewBoardMap) throws DataAccessException {
		List<reViewBoardImgVO> imgFileList = (ArrayList) reViewBoardMap.get("imgFileList");
		int reViewBoardNO = (Integer) reViewBoardMap.get("reViewBoardNO");
		int imgFileNO = selectNewImgFileNO();
		
		// 이미지가 있는 경우만 DB에 insert 
		if (imgFileList.get(0).getImg_filename() != null && !(imgFileList.get(0).getImg_filename().equals("")) ) {
//			System.out.println("이미지 있음");
//			System.out.println("파일명: " + imgFileList.get(0).getImg_filename());
			for (reViewBoardImgVO reViewBoardImgVO : imgFileList) {
				reViewBoardImgVO.setreView_bo_img_no(++imgFileNO);
				reViewBoardImgVO.setreView_bo_no(reViewBoardNO);
			}
			sqlSession.insert(ns + "insertNewImg", imgFileList);
		}
	}

	@Override
	public reViewBoardVO selectreViewBoard(int reViewBoardNO) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectreViewBoard", reViewBoardNO);
	}
	
	@Override
	public List selectImgFileList(int reViewBoardNO) throws DataAccessException {
		List<reViewBoardImgVO> imgFileList = null;
		imgFileList = sqlSession.selectList(ns + "selectImgFileList", reViewBoardNO);
		return imgFileList;
	}

	@Override
	public void updatereViewBoard(Map reViewBoardMap) throws DataAccessException {
		sqlSession.update(ns + "updatereViewBoard", reViewBoardMap);
	}

	@Override
	public void deletereViewBoard(int reViewBoardNO) throws DataAccessException {
		sqlSession.update(ns + "deletereViewBoard", reViewBoardNO);
		// 게시글 이미지 삭제
		sqlSession.update(ns + "deletereViewBoardImg", reViewBoardNO);

	}

	private int selectNewreViewBoardNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewreViewBoardNO");
	}

	private int selectNewImgFileNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewImgFileNO");
	}

	@Override
	public void updateReadCnt(int reViewBoardNO) throws DataAccessException {
		sqlSession.update(ns + "updateReadCnt", reViewBoardNO);
	}

	@Override
	public void updateImgFile(Map reViewBoardMap) throws DataAccessException {
		List<reViewBoardImgVO> imgFileList = (ArrayList) reViewBoardMap.get("imgFileList");
		int reViewBoardNO = Integer.parseInt((String) reViewBoardMap.get("reViewBoardNO"));
		
		for (int i = imgFileList.size() - 1; i >= 0; i--) {
			reViewBoardImgVO reViewBoardImgVO = imgFileList.get(i);
			String imgFileName = reViewBoardImgVO.getImg_filename();
			if (imgFileName == null) { // 기존에 이미지를 수정하지 않는 경우 파일명이 null 이므로 수정할 필요가 없다.
				imgFileList.remove(i);
			} else {
				reViewBoardImgVO.setreView_bo_no(reViewBoardNO);
			}
		}

		if (imgFileList != null && imgFileList.size() != 0) {
			sqlSession.update(ns + "updateImageFile", imgFileList);
		}

	}

	@Override
	public void insertModNewImg(Map reViewBoardMap) throws DataAccessException {
		List<reViewBoardImgVO> modAddimgFileList = (ArrayList<reViewBoardImgVO>) reViewBoardMap.get("modAddimgFileList");
		int reViewBoardNO = Integer.parseInt((String) reViewBoardMap.get("reViewBoardNO"));

		int imgFileNO = selectNewImgFileNO();

		for (reViewBoardImgVO reViewBoardImgVO : modAddimgFileList) {
			reViewBoardImgVO.setreView_bo_no(reViewBoardNO);
			reViewBoardImgVO.setreView_bo_img_no(++imgFileNO);
		}

		sqlSession.insert(ns + "insertModNewImg", modAddimgFileList);
		
	}

	@Override
	public void deleteModImg(reViewBoardImgVO reViewBoardImgVO) throws DataAccessException {
		sqlSession.delete(ns + "deleteModImg", reViewBoardImgVO );		
	}

}
