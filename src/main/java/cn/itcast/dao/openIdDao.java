package cn.itcast.dao;

import cn.itcast.domain.teacherInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.persistence.Table;
import java.util.List;

@Mapper
public interface openIdDao {
    @Select("select * from teacherInfo where personId=#{id}")
    public List<teacherInfo> checkOpenId(String id);

    @Insert("insert into teacherInfo (personId) values(#{personId})")
    public void addTeacherOpenId(String personId);
    @Insert("insert into teacherInfoDetail(teacherId) values(#{teacherId})")
    public void addTeacherDetailOpenId(String teacherId);
}
