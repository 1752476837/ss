package cn.itcast.exception;


import cn.itcast.exception.enums.ExceptionEnum;

/**
 * 封装的抛异常的方法【为了方便】
 * @author Tarry
 * @create 2019/8/9 10:29
 */
public class ExceptionCast {
    public static void cast(ExceptionEnum exceptionEnum){
        throw new YfException(exceptionEnum);
    }
}
