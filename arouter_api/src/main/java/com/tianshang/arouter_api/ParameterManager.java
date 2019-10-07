package com.tianshang.arouter_api;

import android.app.Activity;
import android.util.LruCache;

import androidx.annotation.NonNull;

import com.tianshang.arouter_api.core.ParameterLoad;


/**
 * 参数parameter加载管理器
 */
public class ParameterManager {

    private static ParameterManager instance;
    //Lru缓存，Key：类名 value：parameter加载接口
    private LruCache<String, ParameterLoad> cache;
    //APT生成的获取参数类文件后缀名
    private static final String FILE_SUFFIX_NAME = "$$Parameter";

    public static ParameterManager getInstance() {
        if (instance == null) {
            synchronized (ParameterManager.class) {
                if (instance == null) {
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    private ParameterManager() {
        //设置最大缓存数
        cache = new LruCache<>(163);
    }

    public void loadParameter(@NonNull Activity activity) {
        String className = activity.getClass().getName();
        ParameterLoad iParameter = cache.get(className);
        try {
            if (iParameter == null) {
                Class<?> aClass = Class.forName(className + FILE_SUFFIX_NAME);
                iParameter = (ParameterLoad) aClass.newInstance();
                cache.put(className, iParameter);
            }
            iParameter.loadParameter(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
