package com.marketSpring.board.freeBo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface FreeBoardController {

	public ModelAndView listFreeBoards(@RequestParam Map<String, String> dateMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity addNewFreeBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
	
	public ModelAndView viewFreeBoard(@RequestParam("freeBoardNO") int freeBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ResponseEntity modFreeBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

	public ResponseEntity removeFreeBoard(@RequestParam("freeBoardNO") int freeBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
