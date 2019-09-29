package cn.itcast.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "swiper")
public class swiper {
    @Id
    private Integer id;
    private String type;
    private String url;
}
