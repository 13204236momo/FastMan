package com.tianshang.annotation.behaviour;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用户行为统计
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehaviour {
    String value();
}
