package com.marketSpring.board.freeBo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.marketSpring.board.freeBo.vo.FreeBoardImgVO;
import com.marketSpring.board.freeBo.vo.FreeBoardVO;


@Repository("freeBoardDAO")
public class FreeBoardDAOImpl implements FreeBoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	String ns = "mapper.board.freeBo.";

	@Override
	public List selectAllFreeBoards(Map pagingMap) throws DataAccessException {
		List<FreeBoardVO> freeBoardsList = sqlSession.selectList(ns + "selectAllFreeBoardsList", pagingMap);
		return freeBoardsList;
	}

	@Override
	public int selectTotFreeBoards(Map pagingMap) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectTotFreeBoards", pagingMap);
	}

	@Override
	public int insertNewFreeBoard(Map freeBoardMap) throws DataAccessException {
		int freeBoardNO = selectNewFreeBoardNO();
		freeBoardMap.put("freeBoardNO", freeBoardNO);
		sqlSession.insert(ns + "insertNewFreeBoard", freeBoardMap);
		return freeBoardNO;
	}

	// 다중 파일 업로드
	@Override
	public void insertNewImg(Map freeBoardMap) throws DataAccessException {
		List<FreeBoardImgVO> imgFileList = (ArrayList) freeBoardMap.get("imgFileList");
		int freeBoardNO = (Integer) freeBoardMap.get("freeBoardNO");
		int imgFileNO = selectNewImgFileNO();
		
		// 이미지가 있는 경우만 DB에 insert 
		if (imgFileList.get(0).getImg_filename() != null && !(imgFileList.get(0).getImg_filename().equals("")) ) {
//			System.out.println("이미지 있음");
//			System.out.println("파일명: " + imgFileList.get(0).getImg_filename());
			for (FreeBoardImgVO freeBoardImgVO : imgFileList) {
				freeBoardImgVO.setFree_bo_img_no(++imgFileNO);
				freeBoardImgVO.setFree_bo_no(freeBoardNO);
			}
			sqlSession.insert(ns + "insertNewImg", imgFileList);
		}
	}

	@Override
	public FreeBoardVO selectFreeBoard(int freeBoardNO) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectFreeBoard", freeBoardNO);
	}
	
	@Override
	public List selectImgFileList(int freeBoardNO) throws DataAccessException {
		List<FreeBoardImgVO> imgFileList = null;
		imgFileList = sqlSession.selectList(ns + "selectImgFileList", freeBoardNO);
		return imgFileList;
	}

	@Override
	public void updateFreeBoard(Map freeBoardMap) throws DataAccessException {
		sqlSession.update(ns + "updateFreeBoard", freeBoardMap);
	}

	@Override
	public void deleteFreeBoard(int freeBoardNO) throws DataAccessException {
		sqlSession.update(ns + "deleteFreeBoard", freeBoardNO);
		// 게시글 이미지 삭제
		sqlSession.update(ns + "deleteFreeBoardImg", freeBoardNO);

	}

	private int selectNewFreeBoardNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewFreeBoardNO");
	}

	private int selectNewImgFileNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewImgFileNO");
	}

	@Override
	public void updateReadCnt(int freeBoardNO) throws DataAccessException {
		sqlSession.update(ns + "updateReadCnt", freeBoardNO);
	}

	@Override
	public void updateImgFile(Map freeBoardMap) throws DataAccessException {
		List<FreeBoardImgVO> imgFileList = (ArrayList) freeBoardMap.get("imgFileList");
		int freeBoardNO = Integer.parseInt((String) freeBoardMap.get("freeBoardNO"));
		
		for (int i = imgFileList.size() - 1; i >= 0; i--) {
			FreeBoardImgVO freeBoardImgVO = imgFileList.get(i);
			String imgFileName = freeBoardImgVO.getImg_filename();
			if (imgFileName == null) { // 기존에 이미지를 수정하지 않는 경우 파일명이 null 이므로 수정할 필요가 없다.
				imgFileList.remove(i);
			} else {
				freeBoardImgVO.setFree_bo_no(freeBoardNO);
			}
		}

		if (imgFileList != null && imgFileList.size() != 0) {
			sqlSession.update(ns + "updateImageFile", imgFileList);
		}

	}

	@Override
	public void insertModNewImg(Map freeBoardMap) throws DataAccessException {
		List<FreeBoardImgVO> modAddimgFileList = (ArrayList<FreeBoardImgVO>) freeBoardMap.get("modAddimgFileList");
		int freeBoardNO = Integer.parseInt((String) freeBoardMap.get("freeBoardNO"));

		int imgFileNO = selectNewImgFileNO();

		for (FreeBoardImgVO freeBoardImgVO : modAddimgFileList) {
			freeBoardImgVO.setFree_bo_no(freeBoardNO);
			freeBoardImgVO.setFree_bo_img_no(++imgFileNO);
		}

		sqlSession.insert(ns + "insertModNewImg", modAddimgFileList);
		
	}

	@Override
	public void deleteModImg(FreeBoardImgVO freeBoardImgVO) throws DataAccessException {
		sqlSession.delete(ns + "deleteModImg", freeBoardImgVO );		
	}

}
