package cn.itcast.domain;

import lombok.Data;
import sun.awt.SunHints;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "teacherInfoDetail")
@Data
public class teacherInfoDetail {
    @Id
    @Column( name = "teacherId")
    private String teacherId;
    private String name;
    private String me_photo;
    private String shouke;
    private String description;
    private String anli;
    private String p_photos;
    private int rank;
    private String course_money;
    @Column(name = "basicCourseMoney")
    private String basicCourseMoney;
    private float p_money;


}
