package com.tianshang.fastman.personal.aspect;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.utils.PreferencesUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect //定义切片类
public class LoginCheckAspect {

    private final String TAG = getClass().getSimpleName();

    //1.应用中用到了哪些注解，放入当前的切入点进行处理（找到需要处理的切入点）
    // execution, 以方法执行时作为切点，触发Aspect类
    // @:应为ClickBehavior是一个接口
    // * *(..))":可以处理ClickBehavior这个类的所有方法
    // method() : 方法名可随意
    @Pointcut("execution(@com.tianshang.annotation.behaviour.LoginCheck * *(..))")
    public void method() {
    }

    //2.对这些切入点如何处理
    @Around("method()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取签名方法
        Context context = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法所属类名
        Class<?> clazz = methodSignature.getDeclaringType();

        if (clazz.newInstance() instanceof Fragment){
            Fragment fragment = (Fragment) joinPoint.getThis();
            context = fragment.getContext();
        }else if (clazz.newInstance() instanceof Activity){
            context = (Context)  joinPoint.getThis();
        }

        if (PreferencesUtil.getInstance().isLogin()) { //正式项目从sharedPreferences中读取
            return joinPoint.proceed();
        } else {
            if (context!=null){
                ARouterManager.getInstance()
                        .build("/personal/LoginActivity")
                        .withString("username", "simon")
                        .navigation(context);
            }else {
                throw  new NullPointerException("上下文不能为空");
            }

            return null; //方法不执行（切入点）
        }
    }
}
