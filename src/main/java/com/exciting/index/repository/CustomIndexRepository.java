package com.exciting.index.repository;

import java.util.List;

import com.exciting.dto.AmusementAimageDTO;

public interface CustomIndexRepository {
	List<AmusementAimageDTO> selectListAmuseInfoKoreaDSL();
	List<AmusementAimageDTO> selectListAmuseInfoForeignDSL();
}
