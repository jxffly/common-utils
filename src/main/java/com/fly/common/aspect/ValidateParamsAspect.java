package com.fly.common.aspect;

import com.fly.common.annotation.ValidateParams;
import com.fly.common.model.BusinessException;
import com.fly.common.model.ResponseCode;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;


/**
 * Created by jinxiaofei on 16/10/19.
 */
@Aspect
public class ValidateParamsAspect extends BaseAspect {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateParams.class);
    
    @Resource
    private Validator validator;
    
    
    @Pointcut("@annotation(com.fly.common.annotation.ValidateParams)")
    public void pointCut() {
        
    }
    
    
    @Around("pointCut()")
    Object handle(ProceedingJoinPoint pjp) throws Throwable {
        
        if (validator == null) {
            LOGGER.info("没有初始化校验器,因此不进行参数校验");
            return pjp.proceed();
        }
        Method method = this.getMethod(pjp);
        Object[] args = pjp.getArgs();
        ValidateParams validateParams = method.getAnnotation(ValidateParams.class);
        
        Class[] validateClass = validateParams.validateClass();
        String[] excludes = validateParams.excludes();
        String[] includes = validateParams.includes();
        Set<ConstraintViolation<Object>> set = validate(validateClass, args, excludes, includes);
        if (!set.isEmpty()) {
            String msg = buildMsg(set);
            throw new BusinessException((long) ResponseCode.PARAM_ERROR.getCode(), msg);
        }
        return pjp.proceed();
    }
    
    
    private String buildMsg(Set<ConstraintViolation<Object>> set) {
        
        StringBuilder stringBuilder = new StringBuilder("参数错误:\n");
        for (ConstraintViolation<Object> constraintViolation : set) {
            stringBuilder.append("{").append(constraintViolation.getPropertyPath()).append(":").append
                    (constraintViolation.getInvalidValue()).append(",exception_msg:").append(constraintViolation
                    .getMessage()).append("},");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
    
    
    public Set<ConstraintViolation<Object>> validate(Class[] validateClasses, Object[] args, String[] excludes,
            String[] includes) {
        
        Set<ConstraintViolation<Object>> set = new HashSet<>();
        for (Object arg : args) {
            if (arg instanceof Collection) {
                for (Object el : (Collection) arg) {
                    set.addAll(doValidate(validateClasses, el));
                }
            }else {
                set.addAll(doValidate(validateClasses, arg));
            }
        }
        
        return set.stream()
                //再把需要的属性选择出来
                .filter(constraintViolation -> includes.length <= 0 || Arrays.stream(includes).anyMatch(include -> Objects.equals
                        (include, constraintViolation.getPropertyPath().toString())))
                //先把排除的属性给去除掉
                .filter(constraintViolation -> Arrays.stream(excludes).noneMatch(exclude -> Objects.equals(exclude,
                        constraintViolation.getPropertyPath().toString()))).collect(Collectors.toSet());
    }
    
    
    public Set<ConstraintViolation<Object>> doValidate(Class[] validateClasses, Object arg) {
        
        if (validateClasses == null || validateClasses.length == 0) {
            return Collections.emptySet();
        }else {
            boolean anyMath = Stream.of(validateClasses).anyMatch(validateClass -> Objects.equals(validateClass, arg
                    .getClass()));
            if (anyMath) {
                return validator.validate(arg);
            }else {
                return Collections.emptySet();
            }
        }
    }
}
