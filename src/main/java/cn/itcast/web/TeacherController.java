package cn.itcast.web;

import cn.itcast.domain.teacherInfoDetail;
import cn.itcast.domain.vo.TeacherPublic;
import cn.itcast.exception.YfException;
import cn.itcast.exception.enums.ExceptionEnum;
import cn.itcast.services.TeacherService;
import cn.itcast.until.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //讲师发布
    @PostMapping("public")
    public ResponseEntity teacherPublic(@RequestParam("uid") String uid,@RequestBody String teacherPublic){
        TeacherPublic tp = null;
        try {
            JsonNode jsonNode = JsonUtils.mapper.readTree(teacherPublic).get("teacherPublic");
            tp = JsonUtils.parse(jsonNode.asText(), TeacherPublic.class);
        } catch (IOException e) {
            new YfException(ExceptionEnum.PUBLIC_FAIL);
        }
        tp.setTagStr(JsonUtils.serialize(tp.getTag()));
        System.out.println(tp);

        teacherService.teacherPublic(uid,tp);

        return ResponseEntity.ok().build();
    }



    //添加成功案例
    @PutMapping("successfulCase")
    public ResponseEntity setSuccessfulCase(@RequestParam("uid") String uid,String successfulCase){

        teacherService.setSuccessfulCase(uid,successfulCase);
        return ResponseEntity.ok().build();
    }
    //添加个人描述
    @PutMapping("description")
    public ResponseEntity setDescription(@RequestParam("uid") String uid,String description){

        teacherService.setDescription(uid,description);
        System.out.println("个人描述:"+description);
        return ResponseEntity.ok().build();
    }

    //获取成功案例
    @GetMapping("successfulCase")
    public ResponseEntity<String> getSuccessfulCase(@RequestParam String uid){
        String successfulCase = teacherService.getSuccessfulCase(uid);
        return ResponseEntity.ok(successfulCase);
    }
    //获取个人描述
    @GetMapping("description")
    public ResponseEntity<String> getDescription(@RequestParam String uid){
        String description = teacherService.getDescription(uid);
        return ResponseEntity.ok(description);
    }

    //提交老师授课信息
    @PutMapping("course")
    public ResponseEntity<Void> setTeacherCourseInfo(@RequestParam String uid, String checkArray){
        teacherInfoDetail teacherInfoDetail = new teacherInfoDetail();
        teacherInfoDetail.setTeacherId(uid);
        teacherInfoDetail.setShouke(checkArray);
        teacherService.setTeacherCourseInfo(teacherInfoDetail);
        System.out.println("put课程数据："+teacherInfoDetail);
        return ResponseEntity.ok().build();
    }

    //获取老师授课信息以及头像
    @GetMapping("course")
    public ResponseEntity<Map<String,Object>> getTeacherCourseInfoAndHeadImg(@RequestParam("uid") String uid){
        teacherInfoDetail teacherCourseInfoAndHeadImg = teacherService.getTeacherCourseInfoAndHeadImg(uid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("headImg",teacherCourseInfoAndHeadImg.getMe_photo());
        map.put("checkbox",teacherCourseInfoAndHeadImg.getShouke());

        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);

    }
}
