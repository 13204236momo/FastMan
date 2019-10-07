package com.tianshang.arouter_api.core;

public interface ParameterLoad {
    /**
     * 目标对象.属性名 = target.getIntent().属性类型（"注解值or属性名"）
     * @param target
     */
    void loadParameter(Object target);
}
