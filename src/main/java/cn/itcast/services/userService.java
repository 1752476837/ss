package cn.itcast.services;

import cn.itcast.dao.teacherDao;
import cn.itcast.dao.userDao;
import cn.itcast.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class userService {
    @Autowired
    private userDao uDao;
    @Autowired
    private teacherDao tDao;
    public user getUserTypeAndId(String openid){
        return uDao.getUserTypeAndId(openid);

    }

    public int addUser(String openid,String type){
        String uuid= UUID.randomUUID().toString().replace("-","").toLowerCase();
        return uDao.addUser(uuid,openid,type);
    }
    @Transactional
    public int upType(String type,String id){
             uDao.upType(type,id);
            int ret=0;
            if(tDao.addId(id)>0){
                ret= tDao.addDetailId(id);
            }
            return ret;
    }
    public int test(List<String> wx_openid){
       return uDao.test(wx_openid);
    }

}
