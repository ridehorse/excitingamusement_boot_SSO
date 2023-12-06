package com.exciting.dto;

import com.exciting.entity.FaqEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqDTO {

	private int faq_num;
	private String title;
	private String content;
	private String f_type;

	public FaqDTO(FaqEntity faqEntity) {
		this.faq_num = faqEntity.getFaq_num();
		this.title = faqEntity.getTitle();
		this.content = faqEntity.getContent();
		this.f_type = faqEntity.getF_type();
	}

	public static FaqEntity toEntity(FaqDTO dto ) {
		return FaqEntity.builder()
				.faq_num(dto.getFaq_num())
				.title(dto.getTitle())
				.content(dto.getContent())
				.f_type(dto.getF_type())
				.build();
	}
}
