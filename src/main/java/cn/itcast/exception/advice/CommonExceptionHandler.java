package cn.itcast.exception.advice;

import cn.itcast.exception.ExceptionResult;
import cn.itcast.exception.YfException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//注解拦截所有的带Controller注解的类（启动类的同级目录以内），做AoP环绕通知
@ControllerAdvice
public class CommonExceptionHandler {

    //注解可以拦截指定异常，并将异常传入方法的参数
    @ExceptionHandler(YfException.class)
    public ResponseEntity<ExceptionResult> handleException(YfException e){
        //将编号，和信息给前台
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
