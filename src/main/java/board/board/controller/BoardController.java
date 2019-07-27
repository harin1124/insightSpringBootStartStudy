package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;
import board.common.Common;

@Controller
public class BoardController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private Common common;
	
	// 게시판 목록 진입
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList()throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		return mv;
	}
	
	// 글작성 페이지 진입
	@RequestMapping("/board/openBoardWrite.do")
	public ModelAndView openBoardWrite()throws Exception{
		return new ModelAndView("/board/boardWrite");
	}
	
	// 글작성
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartServletReq)throws Exception{
		boardService.insertBoard(board, multipartServletReq);
		return common.getRedirectAddress("/board/openBoardList.do");
	}
	
	// 상세보기 진입
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx)throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		BoardDto board= boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		return mv;
	}
	
	// 상세보기 내 첨부파일(이미지) 다운로드
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse res)throws Exception{
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(!ObjectUtils.isEmpty(boardFile)){
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			res.setContentType("application/octet-stream");
			res.setContentLength(files.length);
			res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			res.setHeader("Content-Transfer-Encoding", "binary");
			
			res.getOutputStream().write(files);
			res.getOutputStream().flush();
			res.getOutputStream().close();
		}
	}
	
	// 게시글 수정 완료 시
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board)throws Exception{
		boardService.updateBoard(board);
		return common.getRedirectAddress("/board/openBoardList.do");
	}
	
	// 게시글 삭제 시
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx)throws Exception{
		boardService.deleteBoard(boardIdx);
		return common.getRedirectAddress("/board/openBoardList.do");
	}
}