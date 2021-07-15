
  package com.marketSpring.board.qnaBoard.controller;
  
  import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import
  org.springframework.web.bind.annotation.RequestParam;
import
  org.springframework.web.multipart.MultipartHttpServletRequest;
import
  org.springframework.web.servlet.ModelAndView;
  
  public interface qnaBoardController {
  
  public ModelAndView listqnaBoards(@RequestParam Map<String, String> dateMap,
  HttpServletRequest request, HttpServletResponse response) throws Exception;
  
  public ResponseEntity addNewqnaBoard(MultipartHttpServletRequest
  multipartRequest, HttpServletResponse response) throws Exception;
  
  public ModelAndView viewqnaBoard(@RequestParam("qnaBoardNO") int qnaBoardNO,
  HttpServletRequest request, HttpServletResponse response) throws Exception;
  
  public ResponseEntity modqnaBoard(MultipartHttpServletRequest
  multipartRequest, HttpServletResponse response) throws Exception;
  
  public ResponseEntity removeqnaBoard(@RequestParam("qnaBoardNO") int
  qnaBoardNO, HttpServletRequest request, HttpServletResponse response) throws
  Exception;
  
  }
 