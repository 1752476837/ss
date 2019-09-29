package cn.itcast.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class DetailTeacher extends GoldTeacher {
    private List<String> photograph;
    private String description;
    private String successfulCase;


}
