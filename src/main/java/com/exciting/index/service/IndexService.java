package com.exciting.index.service;

import java.util.List;

import com.exciting.dto.AmusementAimageDTO;

public interface IndexService {
	List<AmusementAimageDTO> selectListAmuseInfoKoreaDSL();
	List<AmusementAimageDTO> selectListAmuseInfoForeignDSL();
}
