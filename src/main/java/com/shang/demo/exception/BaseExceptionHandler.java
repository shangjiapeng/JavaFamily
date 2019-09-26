package com.shang.demo.exception;

import com.shang.demo.result.entity.Result;
import com.shang.demo.result.entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>统一异常处理类</p>
 * 为了使我们的代码更容易维护，我们创建一个类集中处理异常
 * @Author: ShangJiaPeng
 * @Since: 2019-07-31 14:43
 */
@ControllerAdvice
//@RestControllerAdvice
public class BaseExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result<>(false, StatusCode.ERROR, e.getMessage(), null);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Object handleDuplicateKeyException(DataAccessException e){
        logger.error(e.getMessage(), e);
        return new Result<>(false, StatusCode.ACCESS_ERROR,"数据库中已存在该记录");
    }

//    @ExceptionHandler(AuthorizationException.class)
//    public Object handleAuthorizationException(AuthorizationException e){
//        logger.error(e.getMessage(), e);
//        return ResultUtil.error("没有权限，请联系管理员授权");
//    }
    
}
