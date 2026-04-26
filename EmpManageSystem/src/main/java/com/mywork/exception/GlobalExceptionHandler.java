package com.mywork.exception;

import com.mywork.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        //有错误原因则返回，否则返回操作失败
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage(): "操作失败");
    }
}
