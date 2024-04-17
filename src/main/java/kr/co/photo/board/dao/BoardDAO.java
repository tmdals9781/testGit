package kr.co.photo.board.dao;

import java.util.List;
import java.util.Map;

import kr.co.photo.board.dto.BoardDTO;

public interface BoardDAO {

	List<BoardDTO> list();

	void del(int idx);

	int write(Map<String, String> param);

	void upHit(String idx);

	BoardDTO detail(String idx);

	void update(Map<String, String> param);

}
