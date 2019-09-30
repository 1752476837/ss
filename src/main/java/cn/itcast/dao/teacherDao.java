package cn.itcast.dao;

import cn.itcast.common.BaseMapper;
import cn.itcast.domain.teacherInfo;
import cn.itcast.domain.teacherInfoDetail;
import cn.itcast.domain.vo.TeacherPublic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface teacherDao extends BaseMapper<teacherInfoDetail,String> {
    @Select("select * from teacherInfoDetail")
    public List<teacherInfoDetail> findAllTeachers();

    //更新老师基本信息
    @Update("update teacherInfo set name=#{name},university=#{university},major=#{major},nianji=#{nianji},flag=1 where teacherId=#{teacherId} and flag=0")
    public int updateTeacher(teacherInfo teacher);

    @Update("update teacherInfoDetail set name=#{name}")
    public int updateTeacherDetailName(String name);

    //更新老师详细信息
    @Update("update teacherInfoDetail set me_photo=#{me_photo},shouke=#{shouke},description=#{description},anli=#{anli},p_photos=#{p_photos},rank=#{rank},course_money=#{course_money},p_money=#{p_money}")
    public int updateTeacherDetail(teacherInfoDetail teacher);

    //获取老师基本信息
    @Select("select * from teacherInfo where teacherId=#{id}")
    public teacherInfo getTeacherBasicInfo(String id);

    @Select("select * from teacherInfoDetail where shouke like CONCAT('%',#{course},'%')")
    public List<teacherInfoDetail> getTeacherByCourse(String course);

    @Select("select * from teacherInfoDetail where rank>=5")
    public List<teacherInfoDetail> getGoldTeachers();

    @Select("select * from teacherInfoDetail where teacherId=#{teacherId}")
    public teacherInfoDetail getDetailById(String teacherId);

    @Select("select course_money from teacherInfoDetail where teacherId=#{teacherId} and course_money like CONCAT('%',#{course_money},'%')")
    public String getTeacherPrice(@Param("teacherId") String teacherId,@Param("course_money") String course_money);

    //执行老师基本信息表插入id
    @Insert("insert into teacherInfo (teacherId) values(#{id})")
    public int addId(String id);
    //执行老师详细信息表插入id
    @Insert("insert into teacherInfoDetail (teacherId) values(#{id})")
    public int addDetailId(String id);




    @Insert("update teacherInfoDetail set me_photo =#{imgUrl} where teacherId=#{id}")
    public int addPhoto(@Param("imgUrl")String url,@Param("id")String id);

    @Select("select shouke from teacherInfoDetail where teacherId=#{id}")
    public String sShouKeByTeacherId(String id);

    @Select("select me_photo from teacherInfoDetail where teacherId=#{id}")
    public String sPhoto(String id);

    //插入薪资
    @Update("update teacherInfoDetail set course_money=#{xinzi} where teacherId=#{id}")
    public int InsertXinziById(@Param("xinzi") String xinzi, @Param("id") String id);
    //查询老师薪资byTeacherid
    @Select("select course_money from teacherInfoDetail where teacherId=#{id}")
    public String getPxinziById(String id);

    @Update("update teacherInfoDetail set anli=#{case} where teacherId=#{uid}")
    void setSuccessfulCase(@Param("uid") String uid,@Param("case") String successfulCase);

    @Select("select anli from teacherInfoDetail where teacherId=#{uid}")
    String getSuccessfulCase(@Param("uid") String uid);

    @Update("update teacherInfoDetail set description=#{desc} where teacherId=#{uid}")
    void setDescription(@Param("uid") String uid, @Param("desc") String description);

    @Select("select description from teacherInfoDetail where teacherId=#{uid}")
    String getDescription(@Param("uid") String uid);

    @Update("update teacherInfoDetail set name=#{public.title},PrimaryGrade=#{public.grade},tag=#{public.tagStr} where teacherId=#{uid}")
    int teacherPublic(String uid,@Param("public") TeacherPublic teacherPublic);
}
