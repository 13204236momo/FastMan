package com.tianshang.fastman.aspect;

import android.util.Log;

import com.tianshang.annotation.behaviour.ClickBehaviour;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect 不支持跨module，目前只能每个module都有个aspect文件夹
 */
@Aspect //定义切片类
public class ClickBehaviorAspect {

    private final String TAG = getClass().getSimpleName();

    //1.应用中用到了哪些注解，放入当前的切入点进行处理（找到需要处理的切入点）
    // execution, 以方法执行时作为切点，触发Aspect类
    // @:应为ClickBehavior是一个接口
    // * *(..))":可以处理ClickBehavior这个类的所有方法
    // method() : 方法名可随意
    @Pointcut("execution(@com.tianshang.annotation.behaviour.ClickBehavior * *(..))")
    public void method() {
    }

    //2.对这些切入点如何处理
    @Around("method()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法所属类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //获取方法名
        String methodName = methodSignature.getName();
        //获取方法的注解值（需要统计的用户行为）
        String funName = methodSignature.getMethod().getAnnotation(ClickBehaviour.class).value();
        //统计方法的执行时间，统计用户点击某功能的行为（存储到本地，每过x天上传到服务器）
        long begin= System.currentTimeMillis();
        Log.e(TAG,"ClickBehavior Method start >>>");
        //MainActivity中切面的方法
        Object result = joinPoint.proceed();
        long duration= System.currentTimeMillis() - begin;
        Log.e(TAG,"ClickBehavior Method end >>>");
        Log.e(TAG,String.format("统计了：%s功能，在%s类的%s方法，用时%s ms",funName,className,methodName,duration));
        return result;
    }
}
