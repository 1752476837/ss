package cn.itcast.services;

import cn.itcast.dao.swiperDao;
import cn.itcast.domain.swiper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class swiperService {
    @Autowired
    private swiperDao swiperDao;
    public List<swiper> getSwipers(){
        return swiperDao.getSwipers();
    }
}
