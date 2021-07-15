package com.marketSpring.board.noticeBo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NoticeBoardController {

	public ModelAndView listNoticeBoards(@RequestParam Map<String, String> dateMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity addNewNoticeBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
	
	public ModelAndView viewNoticeBoard(@RequestParam("noticeBoardNO") int noticeBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ResponseEntity modNoticeBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

	public ResponseEntity removeNoticeBoard(@RequestParam("noticeBoardNO") int noticeBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
