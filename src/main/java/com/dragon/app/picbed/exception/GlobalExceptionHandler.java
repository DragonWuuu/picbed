package com.dragon.app.picbed.exception;

import com.dragon.app.picbed.common.Result;
import com.dragon.app.picbed.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, "参数错误");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<?> AuthenticationExceptionHandler(AuthenticationException e) {
        log.error("AuthenticationException", e);
        return ResultUtils.error(ErrorCode.NO_AUTH_ERROR, "认证失败");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> RuntimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}