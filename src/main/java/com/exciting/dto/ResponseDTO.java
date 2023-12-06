package com.exciting.dto;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error;
	private List<?> data;
	private JSONObject json;
}
