package cn.itcast.dao;

import cn.itcast.domain.swiper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface swiperDao {
    @Select("select * from swiper")
    public List<swiper> getSwipers();
}
