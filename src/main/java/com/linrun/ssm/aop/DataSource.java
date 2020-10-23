package com.linrun.ssm.aop;

import com.linrun.ssm.constant.GlobalConst;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    // 默认业务数据库
    String value() default GlobalConst.DATASOURCE_BUSINESS;
}
