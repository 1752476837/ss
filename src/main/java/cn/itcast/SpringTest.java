package cn.itcast;

import net.sf.json.JSONObject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("cn.itcast.dao")
public class SpringTest {
    private static int x=100;
    public static void main(String[] args) {
        SpringApplication.run(SpringTest.class,args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("5MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("30MB");
        //Sets the directory location where files will be stored.
       // factory.setLocation("C:\\upload");
        return factory.createMultipartConfig();
    }


}
