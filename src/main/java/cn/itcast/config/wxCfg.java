package cn.itcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class wxCfg {
    @Bean
    public RestTemplate rest(){
        return new RestTemplate();
    }
}
