package cn.itcast.exception;


import cn.itcast.exception.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YfException extends RuntimeException {

    private ExceptionEnum exceptionEnum;


}
