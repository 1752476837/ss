package cn.itcast.services;

import cn.itcast.dao.teacherDao;
import cn.itcast.domain.teacherInfo;
import cn.itcast.domain.teacherInfoDetail;
import cn.itcast.domain.vo.GoldTeacher;
import cn.itcast.domain.vo.Salary;
import cn.itcast.domain.vo.TeacherPublic;
import cn.itcast.exception.YfException;
import cn.itcast.exception.enums.ExceptionEnum;
import cn.itcast.until.JsonUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private teacherDao TeacherDao;

    //查询所有老师
    public List<teacherInfoDetail> findAllTeacher(){
        return TeacherDao.findAllTeachers();
    }
    //增加老师基本信息
    public int updateTeacher(teacherInfo teacher){
        System.out.println("updateTeacher services........."+teacher);
        if(TeacherDao.updateTeacher(teacher)>0){
            return TeacherDao.updateTeacherDetailName(teacher.getName());
        }
        return 0;
    }

    //增加老师详细信息
    public int updateTeacherDetail(teacherInfoDetail teacherDetail){
        return TeacherDao.updateTeacherDetail(teacherDetail);
    }

    //根据课程查询老师可以授课科目的老师
    public List<teacherInfoDetail> getTeacherByCourse(String course){
        return TeacherDao.getTeacherByCourse(course);
    }
    //获取金牌讲师rank>=5
    public List<GoldTeacher> getGoldTeachers(){

        List<teacherInfoDetail> list=TeacherDao.getGoldTeachers();
        List<GoldTeacher> goldList=new ArrayList<>();
        int i=0;

        for(teacherInfoDetail t :list){
            GoldTeacher goldTeacher = new GoldTeacher();
            goldTeacher.setTid(t.getTeacherId());
            goldTeacher.setTitle(t.getName());
            goldTeacher.setImage(t.getMe_photo());
            goldTeacher.setDescription(t.getDescription());
            goldTeacher.setPrice(t.getBasicCourseMoney());
            if(StringUtils.isBlank(t.getShouke())){
                //如果老师授课内容为空，则不加入goldList中
                continue;
            }
            List<String> skList = this.parseGoldTag(t.getShouke());

            goldTeacher.setCourse(skList);
            goldList.add(goldTeacher);
        }
        System.out.println("gold教师"+goldList);




        return goldList;
    }

    public List<String> parseGoldTag(String shouke){
        LinkedHashMap<String, ArrayList<String>> map = this.parseShouKe(shouke);
        ArrayList<String> tagList = new ArrayList<>();
        String key=null;
        for (String i : map.keySet()) {
            key = i;
        }
        tagList.add(key);
        ArrayList<String> list = map.get(key);
        for (String item : list){
            if (tagList.size()>=4){
                break;
            }
            tagList.add(item);
        }


        return tagList;
    }


    /**
     * 根据授课shouke字段，解析内容，返回年级和课程的map集合
     * @param shouke
     * @return
     */
    public LinkedHashMap<String, ArrayList<String>>  parseShouKe(String shouke){
        String[][] course ={{"数学","语文","英语"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"}};
        String[] grade = {"小学","初一","初二","初三","高一","高二","高三"};
        if(StringUtils.isBlank(shouke)){
            return null;//字段为null，返回
        }
        JSONArray jsonArray = JSONArray.fromObject(shouke);
        int[][] courseXy =new int[jsonArray.size()][2];
        for (int i=0;i< jsonArray.size();i++){
            for (int j=0;j<jsonArray.getJSONArray(i).size();j++){
                courseXy[i][j]=jsonArray.getJSONArray(i).getInt(j);
            }
        }

        LinkedHashMap<String, ArrayList<String>> courseMap = new LinkedHashMap<>();

        //遍历该老师教的年级
        for (int[] arrs:courseXy){

            if (CollectionUtils.isEmpty(courseMap.get(grade[arrs[0]]))){
                ArrayList<String> list = new ArrayList<>();
                list.add(course[arrs[0]][arrs[1]]);
                courseMap.put(grade[arrs[0]],list);
            }else{
                ArrayList<String> temp = courseMap.get(grade[arrs[0]]);
                temp.add(course[arrs[0]][arrs[1]]);
                courseMap.put(grade[arrs[0]],temp);
            }
        }
        //{初一=[语文, 英语, 物理], 初二=[数学, 语文, 英语, 物理, 化学], 高一=[语文, 英语, 物理]}
        return courseMap;
    }
    //获取老师详细信息
    public teacherInfoDetail getDetailById(String teacherId){
        return TeacherDao.getDetailById(teacherId);

    }

    //获取指定老师指定年级的价格
    public String getTeacherPrice(String teacherId,String course_money){
        System.out.println("services...."+teacherId+course_money);
        return TeacherDao.getTeacherPrice(teacherId,course_money);
    }

    //插入个人头像
    public int addPhoto(String url,String id){
        System.out.println("addPhoto  Service...");
        return TeacherDao.addPhoto(url,id);
    }

    //查询授课信息byteacherid
    public String sShouKeByTeacherId(String id){
        System.out.println("sShouKeByTeacherId service....");
        return TeacherDao.sShouKeByTeacherId(id);
    }
    //查询授课信息byteacherid
    public String sPhotoByTeacherId(String id){
        System.out.println("sPhotoByTeacherId service..");
        return TeacherDao.sPhoto(id);
    }
    //获取老师基本信息
    public teacherInfo getTeacherBasicInfo(String id){
        System.out.println("getTeacherBasicInfo..."+id);
        return TeacherDao.getTeacherBasicInfo(id);
    }

    //插入老师薪资
    public int InsertXinziById(String xinzi,String id){
        System.out.println(xinzi);
        return TeacherDao.InsertXinziById(xinzi,id);
    }

    //查询老师薪资
    public Salary getPxinziById(String id){
        String salaryJson = TeacherDao.getPxinziById(id);
        Salary salary = JsonUtils.parse(salaryJson, Salary.class);
        return salary;
    }

    //提交老师授课年级科目信息
    public void setTeacherCourseInfo(teacherInfoDetail teacherInfoDetail) {
        TeacherDao.updateByPrimaryKey(teacherInfoDetail);
    }

    //获取老师授课年级科目信息+头像
    public teacherInfoDetail getTeacherCourseInfoAndHeadImg(String uid) {
        teacherInfoDetail teacherInfoDetail = new teacherInfoDetail();
        teacherInfoDetail.setTeacherId(uid);

        return  TeacherDao.selectOne(teacherInfoDetail);
    }
    //添加成功案例
    public void setSuccessfulCase(String uid, String successfulCase) {
        TeacherDao.setSuccessfulCase(uid,successfulCase);
    }
    //获取成功案例
    public String getSuccessfulCase(String uid) {
        return TeacherDao.getSuccessfulCase(uid);
    }
    //添加个人描述
    public void setDescription(String uid, String description) {
        TeacherDao.setDescription(uid,description);
    }
    //获取个人描述
    public String getDescription(String uid){
        return TeacherDao.getDescription(uid);
    }

    public void teacherPublic(String uid, TeacherPublic teacherPublic) {

        int i = TeacherDao.teacherPublic(uid, teacherPublic);
        if(i != 1){
            throw new YfException(ExceptionEnum.PUBLIC_FAIL);
        }
    }
}
