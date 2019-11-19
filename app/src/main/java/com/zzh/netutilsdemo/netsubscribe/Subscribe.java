package com.zzh.netutilsdemo.netsubscribe;

import com.zzh.netutilsdemo.bean.User;
import com.zzh.netutilsdemo.netutils.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Description:
 * @Author: zzh
 * @CreateDate: 2019/11/8$ 16:50$
 */
public class Subscribe {

    /**
     * getUserList
     */
    public static void getUserList(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().getUserList();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * getUserResultByUserId
     */
    public static void getUserResultByUserId(String userId, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().getUserResultByUserId(userId);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * getUsersByPage
     */
    public static void getUsersByPage(Map<String, Object> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().getUsersByPage(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * insert
     */
//    public static void insert(User user, DisposableObserver<ResponseBody> subscriber) {
//        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().insert(user);
//        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
//    }

    /**
     * insertUser
     */
    public static void insertUser(User user, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().insertUser(user);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * insertUser1
     */
    public static void insertUser1(String userId, String userName, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().insertUser1(userId, userName);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * insertUser2
     */
    public static void insertUser2(Map<String, Object> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().insertUser2(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * upload
     */
    public static void upload(MultipartBody.Part part, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().upload(part);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * multiUpload
     */
    public static void multiUpload(MultipartBody.Part[] parts, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().multiUpload(parts);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * multiUpload1
     */
    public static void multiUpload1(RequestBody userId, RequestBody content, RequestBody typeList, List<MultipartBody.Part> parts, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().multiUpload1(userId, content, typeList, parts);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * multiUpload2
     */
    public static void multiUpload2(Map<String, RequestBody> map, List<MultipartBody.Part> parts, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().multiUpload2(map, parts);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * test
     */
    public static void test(String age, Map<String, RequestBody> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().test(age, map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }


    public static void downLoadFile(String fileUrl, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable =  RetrofitFactory.getInstance().getHttpApi().downloadFile(fileUrl);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }
}
