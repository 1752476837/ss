package cn.itcast.dao;

import cn.itcast.domain.user;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface userDao {

    @Insert("insert into user (id,wx_openid,type) values(#{id},#{wx_openid},#{type})")

    public int addUser(@Param("id") String id,@Param("wx_openid")String wx_openid,@Param("type") String type);

    @Select("select * from user where wx_openid=#{wx_openid}")
    public user getUserTypeAndId(@Param("wx_openid") String wx_openid);

    @Update("update user set type=#{type} where id=#{id}")
    public int upType(@Param("type") String type,@Param("id")String id);

    @Insert("insert into user (id,wx_openid) values('erwerweiru',#{wx_openid})")
    public int test(@Param("wx_openid")List<String> wx_openid);

}
