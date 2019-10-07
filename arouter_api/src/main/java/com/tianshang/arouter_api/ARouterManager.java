package com.tianshang.arouter_api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.LruCache;

import com.tianshang.annotation.ARouter.model.RouterBean;
import com.tianshang.arouter_api.core.ARouterLoadGroup;
import com.tianshang.arouter_api.core.ARouterLoadPath;


public class ARouterManager {
    private static ARouterManager instance;
    //Lru缓存，key:类名，value:路由组Group加载接口
    private LruCache<String, ARouterLoadGroup> groupLruCache;
    //Lru缓存，key:类名，value:路由组path加载接口
    private LruCache<String, ARouterLoadPath> pathLruCache;
    //路由组名
    private String group;
    //路由path路径
    private String path;
    //APT生成文件的后缀名
    private String GROUP_FILE_PREFIX = "ARouter$$Group$$";


    public static ARouterManager getInstance() {
        if (instance == null) {
            synchronized (ARouterManager.class) {
                if (instance == null) {
                    instance = new ARouterManager();
                }
            }
        }
        return instance;
    }


    private ARouterManager() {
        groupLruCache = new LruCache<>(163);
        pathLruCache = new LruCache<>(163);
    }


    public BundleManger build(String path) {

        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }

        group = subFromPath2Group(path);
        //检查过了path和group
        this.path = path;
        return new BundleManger();
    }

    private String subFromPath2Group(String path) {
        //开发者写法：path = "/MainActivity"
        if (path.lastIndexOf("/") == 0) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }
        String finalGroup = path.substring(1, path.indexOf("/", 1));
        if (TextUtils.isEmpty(finalGroup)) {
            throw new IllegalArgumentException("未按照规范配置，如：/app/MainActivity");
        }
        return finalGroup;
    }


    /**
     * 开始跳转
     *
     * @param context      上下文
     * @param bundleManger 参数的管理
     * @param code         也可以是requestCode ，取决于isResult
     * @return 普通的跳转可以忽略，用于块模块CALL接口
     */
    public Object navigation(Context context, BundleManger bundleManger, int code) {
        String groupClassName = context.getPackageName() + ".apt." + GROUP_FILE_PREFIX + group;
        //Log.e("zhouzhou", groupClassName);

        //读取路由组group类文件（懒加载）
        ARouterLoadGroup groupLoad = groupLruCache.get(group);
        try {
            if (groupLoad == null) {
                //加载APT路由组Group类文件ARouter$$Group$$personal
                Class<?> clazz = Class.forName(groupClassName);
                groupLoad = (ARouterLoadGroup) clazz.newInstance();
                groupLruCache.put(group, groupLoad);
            }
            if (groupLoad.loadGroup().isEmpty()){
                throw new IllegalArgumentException("路由表Group加载失败");
            }

            //读取路由表path路径类文件缓存
            ARouterLoadPath pathLoad = pathLruCache.get(path);
            if (pathLoad == null){
                //通过group加载接口,获取path加载接口
                Class<? extends ARouterLoadPath> aClass = groupLoad.loadGroup().get(group);
                if (aClass != null){
                    pathLoad = aClass.newInstance();
                }
               if (pathLoad != null)pathLruCache.put(path,pathLoad);
            }
            if (pathLoad!=null){
                if (pathLoad.loadPath().isEmpty()){
                    throw new RuntimeException("路由表path加载失败！");
                }
                RouterBean bean = pathLoad.loadPath().get(path);
                if (bean!=null){
                    //类型判断，方便拓展
                    switch (bean.getType()){
                        case ACTIVITY:
                            Intent intent = new Intent(context,bean.getClazz());
                            intent.putExtras(bundleManger.getBundle());

                            if (bundleManger.isResult()){
                                ((Activity) context).setResult(code,intent);
                                ((Activity) context).finish();
                            }
                            if (code>0){ //跳转的时候需要回调
                                ((Activity) context).startActivityForResult(intent,code,bundleManger.getBundle());
                            }else {
                                context.startActivity(intent,bundleManger.getBundle());
                            }
                            break;
                        case CALL:
                            //返回接口的实现类
                            return bean.getClazz().newInstance();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
