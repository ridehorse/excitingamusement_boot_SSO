package com.exciting.dto;

import java.sql.Date;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedNativeQueries;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmusementAimageDTO {
    private int amuse_id;
    private String a_name;
    private String a_country;
    private String a_time;
    private Date a_opening;
    private int a_view;
    private String a_info;
    private double a_lat;
    private double a_lng;
    private int aimg_id;
    private String aimg_name;
    private String url;

    public AmusementAimageDTO(int amuse_id, String a_name, String a_country, String a_time, String url) {
        this.amuse_id = amuse_id;
        this.a_name = a_name;
        this.a_country = a_country;
        this.a_time = a_time;
        this.url = url;
    }
    
    public AmusementAimageDTO(int amuse_id, String a_name, String a_country, String a_time, double a_lat, double a_lng, String url) {
        this.amuse_id = amuse_id;
        this.a_name = a_name;
        this.a_country = a_country;
        this.a_time = a_time;
        this.a_lat = a_lat;
        this.a_lng = a_lng;
        this.url = url;
    }
}
