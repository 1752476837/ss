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
    private Integer price;
    private List<String> tag;  //技能标签  的内容
    private int score;

}
