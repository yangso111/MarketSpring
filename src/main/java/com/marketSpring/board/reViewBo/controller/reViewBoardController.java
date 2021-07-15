package com.marketSpring.board.reViewBo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface reViewBoardController {

	public ModelAndView listreViewBoards(@RequestParam Map<String, String> dateMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ResponseEntity addNewreViewBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
	
	public ModelAndView viewreViewBoard(@RequestParam("reViewBoardNO") int reViewBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ResponseEntity modreViewBoard(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

	public ResponseEntity removereViewBoard(@RequestParam("reViewBoardNO") int reViewBoardNO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
