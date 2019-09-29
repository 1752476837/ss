package cn.itcast.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "teacherInfo")
public class teacherInfo {
    @Id
    private String teacherId;
    private String name;
    private String university;
    private String major;
    private String nianji;
    private Integer flag;
}
