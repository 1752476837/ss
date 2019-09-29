package cn.itcast.services;

import cn.itcast.dao.openIdDao;
import cn.itcast.domain.teacherInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class openIdService {

    @Autowired
    private openIdDao openId;
    public List<teacherInfo> checkOpenId(@Param("id") String id){
        return openId.checkOpenId(id);
    }
    public void addTeacherOpenId(@Param("personId")String personId){
        openId.addTeacherOpenId(personId);
        openId.addTeacherDetailOpenId(personId);
    }
}
