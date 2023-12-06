package com.exciting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectedAmusementAimageDTO {
	private int selected_id;
	private int amuse_id;
	private String a_name;
	private String a_country;
	private String a_time;
	private int aimg_id;
	private String aimg_name;
	private String url;
	
    public SelectedAmusementAimageDTO(int selected_id, int amuse_id, String a_name, String a_country, String a_time, String url) {
        this.selected_id = selected_id;
        this.amuse_id = amuse_id;
        this.a_name = a_name;
        this.a_country = a_country;
        this.a_time = a_time;
        this.url = url;
    }

	
}
