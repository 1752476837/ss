package cn.itcast.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

//    PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
//    CATEGORY_NOT_FOND(404,"商品分类没查到"),

    NEIBU_INFO_FAILE(500,"更新失败"),
    SET_CASE_FAIL(500,"保存成功案例失败"),
    SET_DESCRIPTION_FAIL(500,"个人描述保存失败"),
    PUBLIC_FAIL(500,"教师信息，发布失败"),
    PRICE_ERROR(500,"数据库薪资标准错误"),
    CONTENT_IS_NULL(501,"数据库字段为空，json解析失败")

    ;
    private Integer code;
    private String msg;

}
