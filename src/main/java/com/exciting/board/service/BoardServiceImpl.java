package com.exciting.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.exciting.board.repository.BoardFavoriteRepasitory;
import com.exciting.board.repository.BoardImgRepository;
import com.exciting.board.repository.BoardReplyRepository;
import com.exciting.board.repository.BoardRepository;
import com.exciting.board.repository.JpaSpecification;
import com.exciting.dto.BoardDTO;
import com.exciting.dto.BoardFavoriteDTO;
import com.exciting.dto.BoardImgDTO;
import com.exciting.dto.BoardReplyDTO;
import com.exciting.entity.BoardEntity;
import com.exciting.entity.BoardFavoriteEntity;
import com.exciting.entity.BoardImgEntity;
import com.exciting.entity.BoardReplyEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	BoardRepository boardRepository;
	BoardReplyRepository boardReplyRepository;
	BoardImgRepository boardImgRepository;
	BoardFavoriteRepasitory boardFavoriteRepasitory;

	@Autowired
	public BoardServiceImpl(BoardRepository boardRepository, BoardReplyRepository boardReplyRepository,
			BoardImgRepository boardImgRepository, BoardFavoriteRepasitory boardFavoriteRepasitory) {

		this.boardRepository = boardRepository;
		this.boardReplyRepository = boardReplyRepository;
		this.boardImgRepository = boardImgRepository;
		this.boardFavoriteRepasitory = boardFavoriteRepasitory;
	}

	/*
	 * 
	 * 게시글 낱개 정보 GET Start
	 * 
	 */
	@Override
	public BoardDTO boardView(int board_id) {
		Optional<BoardEntity> boardView = boardRepository.findById(board_id);
		if (boardView.isPresent()) {
			return new BoardDTO(boardView.get());
		}
		return null;
	}

	/*
	 * 
	 * 게시글 낱개 정보 GET END
	 * 
	 */

	/*
	 * 
	 * 게시글 수정 updateBoard GET Start
	 * 
	 */

	public BoardEntity updateBoard(final BoardEntity boardEntity) {
		BoardEntity entity = new BoardEntity();
		try {
			Optional<BoardEntity> boardData = boardRepository.findByBoardId(boardEntity.getBoard_id());

			if (boardData.isPresent()) {
				entity = boardData.get();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return entity;

	}

	/*
	 * 
	 * 게시글 수정 updateBoard GET END
	 * 
	 */

	// 게시글의 댓글 갯수
	public Long boardReplyCnt(int board_id) {
		Long boardReplyCnt = boardReplyRepository.boardReplyCnt(board_id);

		return boardReplyCnt;
	}

	// 조회수 업데이트 처리
	@Override
	public void boardVisit(final BoardEntity entity) {
		try {
			final Optional<BoardEntity> original = boardRepository.findById(entity.getBoard_id());

			original.ifPresent(visit -> {
				entity.setVisitcount(entity.getVisitcount() + 1);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//게시판 이미지 불러오기
	@Override
	public List<BoardImgEntity> boardImgSelect(int board_id) {
		return boardImgRepository.findByBoardId(board_id);
	}

//댓글추가 작업
	@Override
	public void replyInsert(final BoardReplyEntity boardReplyEntity) {

		// 댓글 추가

		try {
			BoardReplyEntity savedBoardReplyEntity = boardReplyRepository.save(boardReplyEntity);
			int getReply_num = savedBoardReplyEntity.getReply_num();
			// 새로 추가된 글
			Optional<BoardReplyEntity> entity = boardReplyRepository.findById(getReply_num);
			// dto로 변환
			Optional<BoardReplyDTO> dto = entity.map(BoardReplyDTO::new);

			// ref갱신
			entity.get().setRef(getReply_num);
			entity.get().setPostdate(LocalDateTime.now());
			BoardReplyEntity savedDTO = entity.get();
			// 업데이트 처리
			boardReplyRepository.save(savedDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ReplyInsert Fail");
		}

	}

	/*
	 * 
	 * 좋아요 favorite 변환작업 Start
	 *
	 */

	@Override
	public void changefavorite(final BoardFavoriteEntity boardFavoriteEntity, int checkData) {

		// 조건문 사용을 위한 선언
		// Specification<BoardFavoriteEntity> spec =
		// Specification.where(JpaSpecification.equalFavoriteDataId(boardFavoriteEntity.getBoard_id()));

		try {
			int board_id = boardFavoriteEntity.getBoard_id();
			String member_id = boardFavoriteEntity.getMember_id();

			// 비교값 로딩
			Optional<BoardFavoriteEntity> favoriteEntity = boardFavoriteRepasitory.getFavoriteData(board_id, member_id);

			favoriteEntity.filter(entity -> entity.getFavorite_num() != 0).ifPresentOrElse(entity -> {
				favoriteWork(favoriteEntity.get(), checkData, boardFavoriteEntity);
			}, () -> {
				BoardFavoriteEntity entity = boardFavoriteRepasitory.save(boardFavoriteEntity);

				favoriteWork(entity, checkData, boardFavoriteEntity);

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("FavoriteUpdate Fail");
		}

	}

	public void favoriteWork(BoardFavoriteEntity BoardFavoriteEntity, int checkData,
			BoardFavoriteEntity boardFavoriteEntity) {

		try {
			BoardFavoriteDTO favoriteDTO = new BoardFavoriteDTO(BoardFavoriteEntity);

			Optional<BoardEntity> boardEntityData = boardRepository.findByBoardId(favoriteDTO.getBoard_id());
			BoardDTO boardDTO = new BoardDTO(boardEntityData.get());

			// if(boardDTO.get().getFavorite())
			int favorite = favoriteDTO.getFavorite();
			int hate = favoriteDTO.getHate();
			int boardFavorite = boardDTO.getFavorite();
			int boardHate = boardDTO.getHate();

			// 버튼이벤트 좋아요 작업
			if (checkData == 1) {
				if (favorite == 0 && hate == 0) {
					favorite += 1;
					boardDTO.setFavorite(boardFavorite + 1);
				} else if (favorite == 1 && hate == 0) {
					favorite -= 1;
					boardDTO.setFavorite(boardFavorite - 1);
				} else if (favorite == 0 && hate == 1) {
					favoriteDTO.setMessage("좋아요와 싫어요는 하나만 선택가능해요");
				}

			}

			// 버튼이벤트 싫어요 작업
			if (checkData == 2) {
				if (favorite == 0 && hate == 0) {
					hate += 1;
					boardDTO.setHate(boardHate + 1);
				} else if (favorite == 0 && hate == 1) {
					hate -= 1;
					boardDTO.setHate(boardHate - 1);
				} else if (favorite == 1 && hate == 0)
					favoriteDTO.setMessage("좋아요와 싫어요는 하나만 선택가능해요");

			}

			// favoriteBoard에 넣을 값 적재
			favoriteDTO.setFavorite(favorite);
			favoriteDTO.setHate(hate);

			// FavoiteBoard DTO -> Entity
			BoardFavoriteEntity favoriteEntity = BoardFavoriteDTO.toEntity(favoriteDTO);
			boardFavoriteRepasitory.save(favoriteEntity);

			// Board DTO -> Entity
			BoardEntity boardEntity = BoardDTO.toEntity(boardDTO);
			boardRepository.save(boardEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("favoriteWork Error");
		}
		// Entity -> DTO

	}
	/*
	 * 
	 * 좋아요 favorite 변환작업 Start
	 *
	 */

	/*
	 * 
	 * 댓글 리스트 출력 Start
	 * 
	 */

	@Override
	public List<BoardReplyEntity> getCommentList(BoardReplyEntity boardReplyEntity) {

		return boardReplyRepository.getCommentList(boardReplyEntity.getBoard_id());
	}

	/*
	 * 
	 * 댓글 리스트 출력 END
	 * 
	 */

	/*
	 * 
	 * 대댓글 추가 Start
	 * 
	 */

	@Override
	public void reReplyInsert(final BoardReplyEntity boardReplyEntity) {
		try {
			int seq = boardReplyRepository.replyData(boardReplyEntity.getRef());

			boardReplyEntity.setSeq(seq + 1);

			boardReplyEntity.setPostdate(LocalDateTime.now());

			boardReplyRepository.save(boardReplyEntity);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("reReplyInsert Error");
		}

	}

	/*
	 * 
	 * 대댓글 추가 END
	 * 
	 */

	/*
	 * 
	 * 댓글 Reply 수정 Update Start
	 * 
	 */

	public void boardReply(final BoardReplyEntity boardReplyEntity) {

		try {
			Optional<BoardReplyEntity> OriginReplyData = boardReplyRepository.findById(boardReplyEntity.getReply_num());

			if (OriginReplyData.isPresent()) {
				OriginReplyData.get().setB_reply(boardReplyEntity.getB_reply());
				OriginReplyData.get().setPostdate(LocalDateTime.now());
				BoardReplyEntity newReplyData = OriginReplyData.get();
				boardReplyRepository.save(newReplyData);
			} else {
				throw new RuntimeException("boardReReplyUpdate Service NotFound Reply Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("boardReReplyUpdate Service Error");
		}

	}

	/*
	 * 
	 * 댓글 Reply 수정 Update End
	 * 
	 */

	/*
	 * 
	 * 댓글 Reply 삭제 Delete End
	 * 
	 */

	public void replyDelete(final BoardReplyEntity boardReplyEntity) {

		try {
			boardReplyRepository.delete(boardReplyEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("boardReplyDelete Service Error");
		}
	}

	/*
	 * 
	 * 댓글 Reply 삭제 Delete End
	 * 
	 */

	/*
	 * 
	 * Board 게시글 update 수정 POST Start
	 * 
	 */
	@Override
	public int commitUpdateBoard(BoardEntity boardEntity) {
		try {

			Optional<BoardEntity> originBoardData = boardRepository.findByBoardId(boardEntity.getBoard_id());

			if (originBoardData.isPresent()) {
				BoardEntity origin = originBoardData.get();	
				origin.setB_content(boardEntity.getB_content());
				origin.setB_title(boardEntity.getB_title());
				origin.setB_type(boardEntity.getB_type());

				boardRepository.save(origin);
			} else {

				throw new RuntimeException("originBoardData Not Found");
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardUpdateCommit Error");
		}
	}

	/*
	 * 
	 * Board 게시글 update 수정 POST End
	 * 
	 */

	/*
	 * 
	 * BoardImg 이미지 upload 업로드 POST Start
	 * 
	 */

	@Override
	public void boardImgInsert(final BoardImgEntity boardImgEntity) {
		try {
			boardImgRepository.save(boardImgEntity);

		} catch (Exception e) {
			throw new RuntimeException("Image insert Fail");
		}

	};

	/*
	 * 
	 * BoardImg 이미지 upload 업로드 POST End
	 * 
	 */

	/*
	 * 
	 * BoardImg 이미지 Delete 삭제 End
	 * 
	 */
	@Override
	@Transactional
	public List<BoardImgEntity> boardImgDelete(final BoardImgEntity boardImgEntity) {

		List<BoardImgEntity> deleteImgData = new ArrayList<>();

		try {

			// 삭제전 정보 가져오기

			if ( boardImgEntity.getBoardimg_num() != null && boardImgEntity.getBoardimg_num() != 0) {
				Optional<BoardImgEntity> deleteImgDataOne = boardImgRepository
						.findById(boardImgEntity.getBoardimg_num());
				BoardImgEntity entity = new BoardImgEntity();
				entity.setBoardimg(deleteImgDataOne.get().getBoardimg());
				deleteImgData.add(entity);
			} else if ( boardImgEntity.getBoard_id() != null && boardImgEntity.getBoard_id() != 0) {
				List<BoardImgEntity> deleteImgDataList = boardImgRepository.findByBoardId(boardImgEntity.getBoard_id());

				deleteImgData = deleteImgDataList;
			}

			// 이미지 DB에서 삭제
			if (deleteImgData != null) {
				boardImgRepository.delete(boardImgEntity);
			} else {
				throw new RuntimeException("BoardImgDelete Service NotFound");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardImgDelete Service Error");
		}

		// 저장해뒀던 기존 데이타 반환
		return deleteImgData;

	}

	/*
	 * 
	 * BoardImg 이미지 Delete 삭제 End
	 * 
	 */

	/*
	 * 
	 * Board 게시물 Delete 삭제 End
	 * 
	 */

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<BoardImgEntity> deleteBoard(final BoardEntity boardentity) {
		
		List<BoardImgEntity> deleteBoardImgs=new ArrayList<>();
		try {
			//이미지 삭제 보관
			List<BoardImgEntity> boardImgEntities = boardImgRepository.findByBoardId(boardentity.getBoard_id());
			
			//이미지 삭제를 위한 보관
			deleteBoardImgs = boardImgEntities;
			
			
			//DB삭제 작업
			boardFavoriteRepasitory.deleteByBoardId(boardentity.getBoard_id());
			boardImgRepository.deleteByBoardId(boardentity.getBoard_id());
			boardReplyRepository.deleteByBoardId(boardentity.getBoard_id());
			boardRepository.deleteById(boardentity.getBoard_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardDelete Service Error");
		}
		return deleteBoardImgs;
	}

	/*
	 * 
	 * Board 게시물 Delete 삭제 End
	 * 
	 */

	/*
	 * 
	 * 게시글쓰기 Writeboard createboard insertboard Start
	 * 
	 */
	
	public int createBoard(final BoardEntity boardEntity) {
		
	
		
		int board_id = 0;
		try {
			boardEntity.setPostdate(LocalDateTime.now());
			
			BoardEntity insertData = boardRepository.save(boardEntity);
			
			Optional<BoardEntity> entity= boardRepository.findById(insertData.getBoard_id());
			
			if(entity.isPresent()) {
				board_id = entity.get().getBoard_id();
			}else {
				throw new RuntimeException("createBoard service NotFoundData");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("createBoard service Error");
		}
		
		
		return board_id;
	}
	

	/*
	 * 
	 * 게시글쓰기 Writeboard createboard insertboard END
	 * 
	 */

}
