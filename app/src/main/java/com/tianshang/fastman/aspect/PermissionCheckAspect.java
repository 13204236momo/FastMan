package com.tianshang.fastman.aspect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.tianshang.annotation.behaviour.ClickBehaviour;
import com.tianshang.annotation.behaviour.PermissionCheck;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.utils.Helper;
import com.tianshang.common.utils.PermissionUtility;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import io.reactivex.functions.Consumer;

@Aspect //定义切片类
public class PermissionCheckAspect {

    private final String TAG = getClass().getSimpleName();

    //1.应用中用到了哪些注解，放入当前的切入点进行处理（找到需要处理的切入点）
    // execution, 以方法执行时作为切点，触发Aspect类
    // @:应为ClickBehavior是一个接口
    // * *(..))":可以处理ClickBehavior这个类的所有方法
    // method() : 方法名可随意
    @Pointcut("execution(@com.tianshang.annotation.behaviour.PermissionCheck * *(..))")
    public void method() {
    }

    //2.对这些切入点如何处理
    @Around("method()")
    public void joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Activity activity = null;
        //获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = methodSignature.getDeclaringType();
        if (clazz.newInstance() instanceof Fragment){
            Fragment fragment = (Fragment) joinPoint.getThis();
            activity = fragment.getActivity();
        }else if (clazz.newInstance() instanceof Activity){
            activity = (Activity) joinPoint.getThis();
        }
        //获取方法的注解值（需要统计的用户行为）
        String[] permissions = methodSignature.getMethod().getAnnotation(PermissionCheck.class).value();

        PermissionUtility.getRxPermission(activity)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted){
                        if (isGranted) {
                            try {
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } else {
                            String tag = "";
                            for (String permission : permissions){
                                if (permission.equals(Manifest.permission.CAMERA)){
                                    tag +="相机、";
                                }
                                if (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
                                    tag +="定位、";
                                }
                                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                    tag +="文件读取";
                                }
                                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                    tag +="文件写入";
                                }
                            }
                            if (tag.endsWith("、")){
                                tag = tag.substring(0,tag.length()-1);
                            }
                            Helper.showToast("请开启"+tag+"等权限");
                            //Toast.makeText(activity,tag,Toast.LENGTH_SHORT).show();
                            Log.e(TAG,tag);
                        }
                    }
                });
    }
}
