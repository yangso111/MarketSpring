package com.marketSpring.board.qnaBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.marketSpring.board.qnaBoard.vo.qnaBoardImgVO;
import com.marketSpring.board.qnaBoard.vo.qnaBoardVO;



@Repository("qnaBoardDAO")
public class qnaBoardDAOImpl implements qnaBoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	String ns = "mapper.board.qnaBo.";

	@Override
	public List selectAllqnaBoards(Map pagingMap) throws DataAccessException {
		List<qnaBoardVO> qnaBoardsList = sqlSession.selectList(ns + "selectAllqnaBoardsList", pagingMap);
		return qnaBoardsList;
	}

	@Override
	public int selectTotqnaBoards(Map pagingMap) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectTotqnaBoards", pagingMap);
	}
   
	@Override
	public int insertNewqnaBoard(Map qnaBoardMap) throws DataAccessException {
		int qnaBoardNO = selectNewqnaBoardNO();
		qnaBoardMap.put("qnaBoardNO", qnaBoardNO);
		sqlSession.insert(ns + "insertNewqnaBoard", qnaBoardMap);
		return qnaBoardNO;
	}

	// 다중 파일 업로드
	@Override
	public void insertNewImg(Map qnaBoardMap) throws DataAccessException {
		List<qnaBoardImgVO> imgFileList = (ArrayList) qnaBoardMap.get("imgFileList");
		int qnaBoardNO = (Integer) qnaBoardMap.get("qnaBoardNO");
		int imgFileNO = selectNewImgFileNO();
		
		// 이미지가 있는 경우만 DB에 insert 
		if (imgFileList.get(0).getImg_filename() != null && !(imgFileList.get(0).getImg_filename().equals("")) ) {
//			System.out.println("이미지 있음");
//			System.out.println("파일명: " + imgFileList.get(0).getImg_filename());
			for (qnaBoardImgVO qnaBoardImgVO : imgFileList) {
				qnaBoardImgVO.setQna_bo_img_no(++imgFileNO);
				qnaBoardImgVO.setQna_bo_no(qnaBoardNO);
			}
			sqlSession.insert(ns + "insertNewImg", imgFileList);
		}
	}

	@Override
	public qnaBoardVO selectqnaBoard(int qnaBoardNO) throws DataAccessException {
		return sqlSession.selectOne(ns + "selectqnaBoard", qnaBoardNO);
	}
	
	@Override
	public List selectImgFileList(int qnaBoardNO) throws DataAccessException {
		List<qnaBoardImgVO> imgFileList = null;
		imgFileList = sqlSession.selectList(ns + "selectImgFileList", qnaBoardNO);
		return imgFileList;
	}

	@Override
	public void updateqnaBoard(Map qnaBoardMap) throws DataAccessException {
		sqlSession.update(ns + "updateqnaBoard", qnaBoardMap);
	}

	@Override
	public void deleteqnaBoard(int qnaBoardNO) throws DataAccessException {
		sqlSession.update(ns + "deleteqnaBoard", qnaBoardNO);
		// 게시글 이미지 삭제
		sqlSession.update(ns + "deleteqnaBoardImg", qnaBoardNO);

	}

	private int selectNewqnaBoardNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewqnaBoardNO");
	}

	private int selectNewImgFileNO() throws DataAccessException {
		return sqlSession.selectOne(ns + "selectNewImgFileNO");
	}

	@Override
	public void updateReadCnt(int qnaBoardNO) throws DataAccessException {
		sqlSession.update(ns + "updateReadCnt", qnaBoardNO);
	}

	@Override
	public void updateImgFile(Map qnaBoardMap) throws DataAccessException {
		List<qnaBoardImgVO> imgFileList = (ArrayList) qnaBoardMap.get("imgFileList");
		int qnaBoardNO = Integer.parseInt((String) qnaBoardMap.get("qnaBoardNO"));
		
		for (int i = imgFileList.size() - 1; i >= 0; i--) {
			qnaBoardImgVO qnaBoardImgVO = imgFileList.get(i);
			String imgFileName = qnaBoardImgVO.getImg_filename();
			if (imgFileName == null) { // 기존에 이미지를 수정하지 않는 경우 파일명이 null 이므로 수정할 필요가 없다.
				imgFileList.remove(i);
			} else {
				qnaBoardImgVO.setQna_bo_no(qnaBoardNO);              //           -------------------------------------------------
			}
		}

		if (imgFileList != null && imgFileList.size() != 0) {
			sqlSession.update(ns + "updateImageFile", imgFileList);
		}

	}

	@Override
	public void insertModNewImg(Map qnaBoardMap) throws DataAccessException {
		List<qnaBoardImgVO> modAddimgFileList = (ArrayList<qnaBoardImgVO>) qnaBoardMap.get("modAddimgFileList");
		int qnaBoardNO = Integer.parseInt((String) qnaBoardMap.get("qnaBoardNO"));

		int imgFileNO = selectNewImgFileNO();

		for (qnaBoardImgVO qnaBoardImgVO : modAddimgFileList) {
			qnaBoardImgVO.setQna_bo_no(qnaBoardNO);
			qnaBoardImgVO.setQna_bo_img_no(++imgFileNO);
		}

		sqlSession.insert(ns + "insertModNewImg", modAddimgFileList);
		
	}


	@Override
	public void deleteModImg(qnaBoardImgVO qnaBoardImgVO) throws DataAccessException {
		sqlSession.delete(ns + "deleteModImg", qnaBoardImgVO );		
	}

}
