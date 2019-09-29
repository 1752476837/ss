package cn.itcast.domain.vo;

import lombok.Data;
import net.sf.json.JSONObject;

import java.util.List;

@Data
public class GoldTeacher {
    private String tid;
    private String title;
    private String image;
    private String description;
    private String price;
    private List<String> course;
    private Double score;

}
