package cn.itcast.web;

import cn.itcast.domain.swiper;
import cn.itcast.domain.teacherInfo;
import cn.itcast.domain.teacherInfoDetail;
import cn.itcast.domain.user;
import cn.itcast.domain.vo.*;
import cn.itcast.exception.ExceptionCast;
import cn.itcast.exception.YfException;
import cn.itcast.exception.enums.ExceptionEnum;
import cn.itcast.services.TeacherService;
import cn.itcast.services.openIdService;
import cn.itcast.services.swiperService;
import cn.itcast.services.userService;
import cn.itcast.until.JsonUtils;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class helloController {


    @Autowired
    private TeacherService teacherService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private openIdService openidService;

    @Autowired
    private userService uService;
    @Autowired
    private swiperService swipers;

    @GetMapping("hello")
    public String hello(){
        System.out.println("hello is running");
        return "hello spring boot";
    }
    //获取老师授课信息，前台列表展示
    @RequestMapping("findAllTeacher")
    public ResponseEntity<List<teacherInfoDetail>> findAllTeacher(){
        //return teacherService.findAllTeacher();
        return new ResponseEntity<List<teacherInfoDetail>>(teacherService.findAllTeacher(),HttpStatus.OK);
    }

    //检查是否存在用户openid如果存在，则返回，不存在进行只将openid存放在数据库
    @GetMapping("login")
    public ResponseEntity<user> addUser(@Param("code")String code){
        String openid=getopenId(code);
       // System.out.println("openid...."+openid);
        int num=0;
        if(uService.getUserTypeAndId(openid)==null){
            num = uService.addUser(openid, "undefined");
            if(num==0){
                throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);
            }
        }
        return new ResponseEntity<user>(uService.getUserTypeAndId(openid),HttpStatus.OK);


    }



    //更新老师基本信息（用户个人中心编辑）
    @PostMapping("teacher/personal")
    public ResponseEntity<Integer> addTeacher(@RequestBody teacherInfo teacher,@RequestParam("uid") String id){
        System.out.println(id);
        teacher.setTeacherId(id);
        System.out.println("set个人信息"+teacher);
        int ret = teacherService.updateTeacher(teacher);
        if(ret>0){
            //添加老师基本信息成功
            return new ResponseEntity<>(ret,HttpStatus.OK);
        }
        //添加老师基本信息失败
        throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);
    }

    //更新老师详细信息（用户个人中心编辑）
    @RequestMapping("addTeacherDetail")
    public ResponseEntity<Integer> addTeacherDetail(teacherInfoDetail teacherDetail){
        int ret=teacherService.updateTeacherDetail(teacherDetail);
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    //获取轮播图信息url
    @GetMapping("swiper")
    public ResponseEntity<List<swiper>> getSwiper(){
        System.out.println("swiper的请求");
        //return swipers.getSwipers();
        return new ResponseEntity<>(swipers.getSwipers(),HttpStatus.OK);
    }


    //获取老师基本信息
    @GetMapping("teacher/personal")
    public ResponseEntity getTeacherBasicInfo(@RequestParam("uid") String id){
        System.out.println("getTeacherBasicInfo...."+id);
        teacherInfo list=teacherService.getTeacherBasicInfo(id);
        return new ResponseEntity<teacherInfo>(list,HttpStatus.OK);
    }

    //更新老师基本信息
    @RequestMapping("updateTeacher")
    public ResponseEntity<Integer> updateTeacher(teacherInfo teacher){
        System.out.println(teacher);
        int ret = teacherService.updateTeacher(teacher);
        if(ret>0){
            //更新老师基本信息成功
            return ResponseEntity.ok().body(ret);
        }
        //更新老师基本信息失败
       throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);
    }
    //更新老师详细信息
    @RequestMapping("updateTeacherDetail")
    public ResponseEntity<Integer> updateTeacherDetail(teacherInfoDetail teacherDetail){
        int ret=teacherService.updateTeacherDetail(teacherDetail);
        return new ResponseEntity(ret, HttpStatus.OK);

    }

    @GetMapping("item/list/{course}")
    public ResponseEntity<List<teacherInfoDetail>> getTeacherByCourse(@PathVariable("course") String course){
        System.out.println(course);

        //return teacherService.getTeacherByCourse(course);
        return new ResponseEntity<>(teacherService.getTeacherByCourse(course),HttpStatus.OK);
    }

    //金牌讲师
    @GetMapping("gold")
    public ResponseEntity<List<GoldTeacher>> getGoldTeacher(){
        return new ResponseEntity<List<GoldTeacher>>(teacherService.getGoldTeachers(),HttpStatus.OK);
    }

    //老师详情
    @GetMapping("item/detail/{tid}")
    public ResponseEntity<DetailTeacher> getTeacherDetail(@PathVariable("tid") String teacherId){
        teacherInfoDetail d = teacherService.getDetailById(teacherId);


            DetailTeacher dt=new DetailTeacher();
            dt.setTid(d.getTeacherId());
            dt.setDescription(d.getDescription());
            if (StringUtils.isNotBlank(d.getP_photos())){
                dt.setPhotograph(JsonUtils.parseList(d.getP_photos(),String.class));
            }
            dt.setSuccessfulCase(d.getAnli());
            dt.setTitle(d.getName());
            dt.setImage(d.getMe_photo());
            dt.setDescription(d.getDescription());
            dt.setCourse(d.getShouke());

        return new ResponseEntity<DetailTeacher>(dt,HttpStatus.OK);
    }

    //指定id初几返回价格
    @GetMapping("item/schedule/{tid}/{grade}")
    public ResponseEntity<String> getTeacherPrice(@PathVariable("tid") String teacherId,@PathVariable("grade") String course_money){
        System.out.println(teacherId+course_money);
        String ret=teacherService.getTeacherPrice(teacherId,course_money);
        System.out.println(ret);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = gson.fromJson(ret, map.getClass());
            String price = (String) map.get(course_money);
            System.out.println(course_money+"价格为:"+price);
            return new ResponseEntity<String>(price,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("错误",HttpStatus.BAD_GATEWAY);
        }
    }

    //根据openid获取是老师还是家长
    @GetMapping("user/type")
    public ResponseEntity<user> getUserType(@RequestParam("openid")String openid){

        //System.out.println(openid);
        try {
            return new ResponseEntity<user>(uService.getUserTypeAndId(openid),HttpStatus.OK);
        }catch (Exception e)
        {
            throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);
        }

    }

    //插入老师或学生表id
    @GetMapping("user/identity")
    public ResponseEntity AddId(@RequestParam("uid") String uuid,@RequestParam("status") String type){
        System.out.println("user/identity.controll...."+uuid+"...."+type);
        if(uService.upType(type,uuid)>0){
                return new ResponseEntity(HttpStatus.OK);
        }
        throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);

    }

    //获取openid函数
    private String getopenId(String code){
        System.out.println(code);
        String appid="wxafca2f661eba4d04";
        String secret="5d30814a7fd6fd06f4ec73954f2436b2";
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String jsonData = this.restTemplate.getForObject(url, String.class);
        JSONObject jo= JSONObject.fromObject(jsonData);
        String openId="null";
        try {
            openId=jo.getString("openid");
        }catch (Exception e){
            System.out.println(e);
            return "fail";
        }
        return openId;
    }




    //上传个人图片头像
    @RequestMapping("/upload/image")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload27Niu(@RequestParam("file") MultipartFile file,@RequestParam("uid") String uid) {
        System.out.println("正在上传file...."+file+uid);
        String imgUrl="";
        if (!file.isEmpty()) {

            System.out.println(file.getOriginalFilename());
            System.out.println(file.getName());
//            System.out.println(file.get());
            BufferedImage image = null;
            try {
                image = ImageIO.read(file.getInputStream());
                System.out.println("image.."+image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(image == null){
                throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);
            }
            Configuration cfg = new Configuration(Zone.zone2());//华南

            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String accessKey = "_NZvWWEt5vgkmcOBKqn14BI_uiz0HbSEBtAaBj6q";
            String secretKey = "mKJaPPURnQ0p1nlt86bkwZUCc7jiIU159-sXcrxR";
            String bucket = "yfteach1";
            //如果是Windows情况下，格式是 D:\\qiniu\\test.png
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = UUID.randomUUID().toString().replace("-","").toLowerCase();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
//                Response response = uploadManager.put(localFilePath, key, upToken);
                Response response = uploadManager.put(file.getBytes(), key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.toString());
                imgUrl="http://pyhbfnxa4.bkt.clouddn.com/"+putRet.key;
                System.out.println("domain   "+"http://pyhbfnxa4.bkt.clouddn.com/"+putRet.key);
                System.out.println("putRet.hash-->   "+putRet.hash);
                String[] ll=new String[]{imgUrl};
                String jsonImg = JsonUtils.serialize(ll);

                teacherService.addPhoto(jsonImg,uid);

            } catch (Exception ex) {

                System.err.println(ex.toString());
                throw new YfException(ExceptionEnum.NEIBU_INFO_FAILE);

            }
        }
        return new ResponseEntity<String>(imgUrl,HttpStatus.OK);
    }



    //更改老师薪资
    @PutMapping("teacher/salary")
    public ResponseEntity addPxinzi(@RequestBody Salary salary, @RequestParam("uid")String id){
        System.out.println("修改薪资："+salary);
        String salaryStr = JsonUtils.serialize(salary);

        teacherService.InsertXinziById(salaryStr,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //查询老师薪资
    @GetMapping("teacher/salary")
    public ResponseEntity<Salary> getPxinzi(@RequestParam("uid") String id){
        Salary salary = teacherService.getPxinziById(id);
        log.info("查询薪资：{}",salary);
        return new ResponseEntity<>(salary,HttpStatus.OK);
    }
}
