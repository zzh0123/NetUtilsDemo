package com.zzh.netutilsdemo.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
