package com.marketSpring.board.qnaBoard.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.marketSpring.board.qnaBoard.vo.qnaBoardImgVO;
import com.marketSpring.board.qnaBoard.service.qnaBoardService;
import com.marketSpring.board.qnaBoard.vo.qnaBoardVO;
import com.marketSpring.member.vo.MemberVO;

@Controller("qnaBoardController")
public class qnaBoardControllerImpl implements qnaBoardController {
	private static final String QNABO_IMAGE_REPO = "C:\\board\\qnaBoard_img";

	@Autowired
	private qnaBoardService qnaBoardService;
	@Autowired
	private qnaBoardVO qnaBoardVO;

	@Override
	@RequestMapping(value = "/board/qnaBo/listqnaBoards.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listqnaBoards(@RequestParam Map<String, String> dateMap,
									HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("qnaBoardController listqnaBoards() " + new Date());
		
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu", "board");		// 게시판 사이드 메뉴로 설정한다.

		// 페이징
		String _section = dateMap.get("section");
		String _pageNum = dateMap.get("pageNum");
		int section = Integer.parseInt(((_section == null) ? "1" : _section));
		int pageNum = Integer.parseInt(((_pageNum == null) ? "1" : _pageNum));
		
		Map<String, Object> pagingMap = new HashMap<String, Object>();
		pagingMap.put("section", section);
		pagingMap.put("pageNum", pageNum);
		
		/* 검색 */
		
		  String s_category = request.getParameter("s_category"); String s_keyword =
		  request.getParameter("s_keyword"); pagingMap.put("s_category", s_category);
		  pagingMap.put("s_keyword", s_keyword);
		 
		
		Map qnaBoardsMap = qnaBoardService.listqnaBoards(pagingMap); // 모든 글 정보를 조회(페이징 적용)		

		qnaBoardsMap.put("section", section);
		qnaBoardsMap.put("pageNum", pageNum);
		
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("qnaBoardsMap", qnaBoardsMap); // 조회한 글 정보를 바인딩한 후 JSP로 전달
		
//		/* 페이징 */
//		String _sn = request.getParameter("pageNumber");
//		String _recordCountPerPage = request.getParameter("recordCountPerPage");
//		int sn = Integer.parseInt(((_sn==null)?"0":_sn));
//		int recordCountPerPage = Integer.parseInt(((_recordCountPerPage==null)?"10":_recordCountPerPage));
//		int start = (sn) * recordCountPerPage + 1;
//		int end = (sn + 1) * recordCountPerPage;
//
//		Map<String,Object> pagingMap = new HashMap<String,Object>();
//		
//		pagingMap.put("start", start);
//		pagingMap.put("end", end);
//		
//		/* 검색 */
//		String s_category = request.getParameter("s_category");
//		String s_keyword = request.getParameter("s_keyword");
//		pagingMap.put("s_category", s_category);
//		pagingMap.put("s_keyword", s_keyword);
//
//		//List freeBoardsList = freeBoardService.listFreeBoards(pagingMap); // 모든 글 정보를 조회
//		Map freeBoardsMap = freeBoardService.listFreeBoards(pagingMap); // 모든 글 정보를 조회
//		
//		freeBoardsMap.put("start", start);
//		freeBoardsMap.put("end", end);
//
//		freeBoardsMap.put("pageNumber", sn);
//		freeBoardsMap.put("pageCountPerScreen", 10);
//		freeBoardsMap.put("recordCountPerPage", request.getParameter("recordCountPerPage"));
//		
//		/* 검색 */
//		freeBoardsMap.put("s_category", request.getParameter("s_category"));
//		freeBoardsMap.put("s_keyword", request.getParameter("s_keyword"));
		
//		request.setAttribute("freeBoardsMap", freeBoardsMap);

		return mav;
	}
	
	
	// 다중 이미지 글 추가하기
	@Override
	@RequestMapping(value = "/board/qnaBo/addNewqnaBoard.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewqnaBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		String imgFileName = null;

		Map qnaBoardMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			qnaBoardMap.put(name, value);
		}

		// 로그인 시 세션에 저장된 회원 정보에서 글쓴이 아이디를 얻어와서 Map에 저장합니다.
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String mem_id = memberVO.getMember_id();
		qnaBoardMap.put("mem_id", mem_id);
		String parentNO = (String)session.getAttribute("parentNO");
		qnaBoardMap.put("parentNO", (parentNO == null ? 0 : parentNO));
		System.out.println("parentNO : "+parentNO);

		List<String> fileList = upload(multipartRequest);
		List<qnaBoardImgVO> imgFileList = new ArrayList<qnaBoardImgVO>();
		if (fileList != null && fileList.size() != 0) {
			for (String fileName : fileList) {
				qnaBoardImgVO qnaBoardImgVO = new qnaBoardImgVO();
				qnaBoardImgVO.setImg_filename(fileName);
				imgFileList.add(qnaBoardImgVO);
			}
			qnaBoardMap.put("imgFileList", imgFileList);
		}
		
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int qnaBoardNO = qnaBoardService.addNewqnaBoard(qnaBoardMap);
			if (imgFileList != null && imgFileList.size() != 0 && !(imgFileList.get(0).getImg_filename().equals("")) ) {
			// && !(imgFileList.get(0).getImg_filename().equals("")) 이미지 등록하지 않는 경우도 있어서 추가
//			if (imgFileList != null && imgFileList.size() != 0) {
				for (qnaBoardImgVO qnaBoardimgVO : imgFileList) {
					imgFileName = qnaBoardimgVO.getImg_filename();
					File srcFile = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + imgFileName);
					File destDir = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO);
					// destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			session.removeAttribute("parentNO");	// 답글쓰기 후 새글을 위해 부모값 초기화 
			
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/qnaBo/listqnaBoards.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);

		} catch (Exception e) {
			if (imgFileList != null && imgFileList.size() != 0) {
				for (qnaBoardImgVO qnaBoardimgVO : imgFileList) {
					imgFileName = qnaBoardimgVO.getImg_filename();
					File srcFile = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + imgFileName);
					srcFile.delete();
				}
			}

			message = " <script>";
			message += " alert('오류가 발생했습니다. 다시 시도해 주세요');');";

			message += " location.href='" + multipartRequest.getContextPath() + "/board/qnaBo/qnaBoardForm.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		return resEnt;
	}
	
	// 다중 이미지 보여주기
	@RequestMapping(value = "/board/qnaBo/viewqnaBoard.do", method = RequestMethod.GET)
	public ModelAndView viewqnaBoard(@RequestParam("qnaBoardNO") int qnaBoardNO, 
									HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		Map qnaBoardMap = qnaBoardService.viewqnaBoard(qnaBoardNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("qnaBoardMap", qnaBoardMap);
		return mav;
	}

	@Override
	@RequestMapping(value = "/board/qnaBo/removeqnaBoard.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity removeqnaBoard(@RequestParam("qnaBoardNO") int qnaBoardNO, 
										HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			qnaBoardService.removeqnaBoard(qnaBoardNO);
			File destDir = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO);
			FileUtils.deleteDirectory(destDir);

			message = "<script>";
			message += " alert('글을 삭제했습니다.');";
			message += " location.href='" + request.getContextPath() + "/board/qnaBo/listqnaBoards.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);

		} catch (Exception e) {
			message = "<script>";
			message += " alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
			message += " location.href='" + request.getContextPath() + "/board/qnaBo/listqnaBoards.do';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}

	// 다중 이미지 수정 기능
	@RequestMapping(value = "/board/qnaBo/modqnaBoard.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modqnaBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");

		Map<String, Object> qnaBoardMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();

			if (name.equals("imgFileNO")) {
				String[] values = multipartRequest.getParameterValues(name);
				qnaBoardMap.put(name, values);
			} else if (name.equals("oldFileName")) {
				String[] values = multipartRequest.getParameterValues(name);
				qnaBoardMap.put(name, values);
			} else {
				String value = multipartRequest.getParameter(name);
				qnaBoardMap.put(name, value);
			}

		}

		List<String> fileList = uploadModImgFile(multipartRequest);

		int added_img_num = Integer.parseInt((String) qnaBoardMap.get("added_img_num"));
		int pre_img_num = Integer.parseInt((String) qnaBoardMap.get("pre_img_num"));
		List<qnaBoardImgVO> imgFileList = new ArrayList<qnaBoardImgVO>();
		List<qnaBoardImgVO> modAddimgFileList = new ArrayList<qnaBoardImgVO>();

		if (fileList != null && fileList.size() != 0) {
			String[] imageFileNO = (String[]) qnaBoardMap.get("imgFileNO");
			for (int i = 0; i < added_img_num; i++) {
				String fileName = fileList.get(i);
				qnaBoardImgVO qnaBoardImgVO = new qnaBoardImgVO();
				if (i < pre_img_num) {
					qnaBoardImgVO.setImg_filename(fileName);
					qnaBoardImgVO.setQna_bo_img_no(Integer.parseInt(imageFileNO[i]));
					imgFileList.add(qnaBoardImgVO);
					qnaBoardMap.put("imgFileList", imgFileList);
				} else {
					qnaBoardImgVO.setImg_filename(fileName);
//					freeBoardImgVO.setImageFileNO(Integer.parseInt(imageFileNO[i]));
					modAddimgFileList.add(qnaBoardImgVO);
					qnaBoardMap.put("modAddimgFileList", modAddimgFileList);
				}
			}
		}

		String qnaBoardNO = (String) qnaBoardMap.get("qnaBoardNO");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			qnaBoardService.modqnaBoard(qnaBoardMap);
			if (fileList != null && fileList.size() != 0) { // 수정한 파일들을 차례대로 업로드한다.
				for (int i = 0; i < fileList.size(); i++) {
					String fileName = fileList.get(i);
					if (i < pre_img_num) {
						if (fileName != null) {
							File srcFile = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + fileName);
							File destDir = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO);
							FileUtils.moveFileToDirectory(srcFile, destDir, true);

							String[] oldName = (String[]) qnaBoardMap.get("oldFileName");
							String oldFileName = oldName[i];

							File oldFile = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO + "\\" + oldFileName);
							oldFile.delete();
						}
					} else {
						if (fileName != null) {
							File srcFile = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + fileName);
							File destDir = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO);
							FileUtils.moveFileToDirectory(srcFile, destDir, true);
						}
					}
				}
			}
			message = "<script>";
			message += " alert('글을 수정했습니다.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/qnaBo/viewqnaBoard.do?qnaBoardNO=" + qnaBoardNO + "';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			if (fileList != null && fileList.size() != 0) { // 오류 발생 시 temp 폴더에 업로드된 이미지 파일들을 삭제한다.
				for (int i = 0; i < fileList.size(); i++) {
					File srcFile = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + fileList.get(i));
					srcFile.delete();
				}
				e.printStackTrace();
			}

			message = "<script>";
			message += " alert('오류가 발생했습니다.다시 수정해주세요');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/qnaBo/viewqnaBoard.do?qnaBoardNO=" + qnaBoardNO + "';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}

		return resEnt;
	}

	// 수정하기에서 이미지 삭제 기능
	@RequestMapping(value = "/board/qnaBo/removeModImage.do", method = RequestMethod.POST)
	@ResponseBody
	public void removeModImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();

		try {
			String imgFileNO = (String) request.getParameter("imageFileNO");
			String imgFileName = (String) request.getParameter("imgFileName");
			String qnaBoardNO = (String) request.getParameter("qnaBoardNO");

			qnaBoardImgVO qnaBoardImgVO = new qnaBoardImgVO();
			qnaBoardImgVO.setQna_bo_no(Integer.parseInt(qnaBoardNO));
			qnaBoardImgVO.setQna_bo_img_no(Integer.parseInt(imgFileNO));
			qnaBoardService.removeModImg(qnaBoardImgVO);

			File oldFile = new File(QNABO_IMAGE_REPO + "\\" + qnaBoardNO + "\\" + imgFileName);
			oldFile.delete();

			writer.print("success");
		} catch (Exception e) {
			writer.print("failed");
		}

	}

	@RequestMapping(value = "/board/qnaBo/*Form.do", method = {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							  @RequestParam(value= "action", required=false) String action,
							  @RequestParam(value= "parentNO", required=false) String parentNO,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		//System.out.println(">>>>>action: "+ action);
		System.out.println("parentNO>>>> "+parentNO);
		if(parentNO != null) {   //답글쓰기
			session.setAttribute("parentNO", parentNO);
		} 
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);

		return mav;
	}	

	// 다중 이미지 업로드하기
	// 새 글 쓰기 시 다중 이미지 업로드하기
	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + fileName);
			if (mFile.getSize() != 0) { // File Null Check
				if (!file.exists()) { 	// 경로상에 파일이 존재하지 않을 경우
					file.getParentFile().mkdirs();	// 경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(QNABO_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송 } }
				}
			}
		}
		return fileList;
	}

	// 수정 시 다중 이미지 업로드하기
	private List<String> uploadModImgFile(MultipartHttpServletRequest multipartRequest) throws Exception {
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			if (originalFileName != "" && originalFileName != null) {
				fileList.add(originalFileName);
				File file = new File(QNABO_IMAGE_REPO + "\\" + fileName);
				if (mFile.getSize() != 0) { // File Null Check
					if (!file.exists()) { // 경로상에 파일이 존재하지 않을 경우
						file.getParentFile().mkdirs(); // 경로에 해당하는 디렉토리들을 생성
						mFile.transferTo(new File(QNABO_IMAGE_REPO + "\\" + "temp" + "\\" + originalFileName)); // 임시로 저장된 multipartFile을 실제 파일로 전송
					}
				}
			} else {
				fileList.add(null);
			}

		}
		return fileList;
	}
	
	
	
}
