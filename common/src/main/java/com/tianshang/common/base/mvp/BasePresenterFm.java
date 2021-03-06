package com.tianshang.common.base.mvp;

import java.lang.ref.WeakReference;

//Presenter基类
public abstract class BasePresenterFm<V extends BaseViewFm, CONTRACT> {

    //protected M m;
    //绑定view层弱引用
    private WeakReference<V> vWeakReference;


//    public BasePresenterFm() {
//        m = getModel();
//    }

    //获取子类具体契约（model和view层协商的共同业务）
    public abstract CONTRACT getContract();

    //public abstract M getModel();

    public void bindView(V v) {
        vWeakReference = new WeakReference<>(v);
    }

    //从弱引用获取view
    public V getView(){
        if (vWeakReference!=null){
            return vWeakReference.get();
        }
        return null;
    }

    //解绑
    public void unBindView() {
        if (vWeakReference != null) {
            vWeakReference.clear();
            vWeakReference = null;
            System.gc();
        }
    }
}
