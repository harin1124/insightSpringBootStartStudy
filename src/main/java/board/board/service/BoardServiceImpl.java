package board.board.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;

@Service
public class BoardServiceImpl implements BoardService{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private FileUtils fileUtils;

	@Override
	public List<BoardDto> selectBoardList()throws Exception{
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletReq)throws Exception{
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletReq);
		if(!CollectionUtils.isEmpty(list)){
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx)throws Exception{
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		board.setFileList(boardMapper.selectBoardFileList(boardIdx));
		boardMapper.updateHitCount(boardIdx);
		return board;
	}

	@Override
	public void updateBoard(BoardDto board)throws Exception{
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx)throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx)throws Exception{
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
}