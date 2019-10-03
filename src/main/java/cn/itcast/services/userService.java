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
            if("teacher".equals(type)){
                tDao.addId(id);
                tDao.addDetailId(id);
                return 1;
            }else if("parent".equals(type)){
                //在家长信息表中新增记录，暂时没写

                return 1;
            }
            return 0;
    }
    public int test(List<String> wx_openid){
       return uDao.test(wx_openid);
    }

}
