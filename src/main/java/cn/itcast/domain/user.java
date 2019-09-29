package cn.itcast.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Data
public class user {
    @Id
    private String id;
    private String wx_openid;
    private String type;
}
