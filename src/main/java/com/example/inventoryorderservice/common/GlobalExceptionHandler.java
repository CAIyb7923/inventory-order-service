package com.example.inventoryorderservice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        log.warn("业务异常：{}", e.getMessage());

        return Result.error(e.getErrorCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        log.warn("参数校验失败：{}", msg);

        return Result.error(ErrorCode.BAD_REQUEST.getCode(), msg);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public Result<Void> handleOptimisticLock(ObjectOptimisticLockingFailureException e) {
        log.warn("乐观锁冲突：{}", e.getMessage());

        return Result.error(
                ErrorCode.ORDER_VERSION_CONFLICT.getCode(),
                ErrorCode.ORDER_VERSION_CONFLICT.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage());
    }
}
