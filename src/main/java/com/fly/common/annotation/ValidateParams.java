package com.fly.common.annotation;

import java.lang.annotation.*;

/**
 * Created by jinxiaofei on 16/10/19.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateParams {
    Class[] validateClass() default {};
    String[] excludes() default {};
    
    String[] includes() default {};
}
