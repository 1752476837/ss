package cn.itcast.domain.vo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author Tarry
 * @create 2019/9/29 16:57
 */
@Data
public class TeacherPublic {
    private String title;
    private String[] tag;
    private Integer grade;
    private String tagStr;
}
