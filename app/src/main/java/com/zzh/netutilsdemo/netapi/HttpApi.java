package com.zzh.netutilsdemo.netapi;


import com.zzh.netutilsdemo.bean.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 存放所有的Api
 */

public interface HttpApi {
    // get请求
    @GET("user/getUserList")
    Observable<ResponseBody> getUserList();

    @GET("user/getUserResultByUserId")
    Observable<ResponseBody> getUserResultByUserId(@Query("userId") String userId);

    @GET("user/getUsersByPage")
    Observable<ResponseBody> getUsersByPage(@QueryMap Map<String, Object> map);

    // post请求
    @POST("user/insertUser")
    Observable<ResponseBody> insertUser(@Body User user);

    @POST("user/insertUser1")
    @FormUrlEncoded
    Observable<ResponseBody> insertUser1(@Field("userId") String userId, @Field("userName") String userName);

    @POST("user/insertUser2")
    @FormUrlEncoded
    Observable<ResponseBody> insertUser2(@FieldMap Map<String, Object> map);


    /**
     * 文件下载
     */
    @GET()
    @Streaming//使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    Observable<ResponseBody> downloadFile(@Url String fileUrl);//返回值使用 ResponseBody 之后会对ResponseBody 进行读取

    /**
     *  文件上传
     */
    // 传单张图片
    @POST("share/upload")
    @Multipart
    Observable<ResponseBody> upload(@Part MultipartBody.Part part);

    // 传多张图片
    @POST("share/multiUpload")
    @Multipart
    Observable<ResponseBody> multiUpload(@Part MultipartBody.Part[] parts);

    // 传参数 + 多张图片
    @POST("share/multiUpload1")
    @Multipart
    Observable<ResponseBody> multiUpload1(@Part("userId") RequestBody userId, @Part("content") RequestBody content,
                                          @Part("typeList") RequestBody typeList,
                                          @Part() List<MultipartBody.Part> parts);

    // 传参数 + 多张图片(建议)
    @POST("share/multiUpload2")
    @Multipart
    Observable<ResponseBody> multiUpload2(@PartMap Map<String, RequestBody> map,
                                          @Part() List<MultipartBody.Part> parts);

    @POST("share/test")
    @Multipart
    Observable<ResponseBody> test(@Part("age") String age, @PartMap Map<String, RequestBody> map);
//    Observable<ResponseBody> test(@Body RequestBody typeList);
}
