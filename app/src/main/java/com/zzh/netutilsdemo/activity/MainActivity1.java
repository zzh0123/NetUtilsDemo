package com.zzh.netutilsdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zzh.netutilsdemo.R;
import com.zzh.netutilsdemo.bean.ResultInfo;
import com.zzh.netutilsdemo.bean.User;
import com.zzh.netutilsdemo.netsubscribe.Subscribe;
import com.zzh.netutilsdemo.netutils.OnSuccessAndFaultListener;
import com.zzh.netutilsdemo.netutils.OnSuccessAndFaultSub;
import com.zzh.netutilsdemo.presenter.FilePresenter;
import com.zzh.netutilsdemo.utils.GsonUtils;
import com.zzh.netutilsdemo.view.FileView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity1 extends AppCompatActivity implements FileView {

    @BindView(R.id.tv_result) //绑定tv_result控件
    public TextView tv_result;
    
    @BindView(R.id.tv_getUserList) //绑定tv_result控件
    public TextView tv_getUserList;

    @BindView(R.id.tv_getUserResultByUserId) //绑定tv_result控件
    public TextView tv_getUserResultByUserId;

    @BindView(R.id.tv_getUsersByPage) //绑定tv_result控件
    public TextView tv_getUsersByPage;

    @BindView(R.id.tv_result3) //绑定tv_result控件
    public TextView tv_result3;

    @BindView(R.id.tv_insertUser4) //绑定tv_result控件
    public TextView tv_insertUser4;

    @BindView(R.id.tv_insertUser1) //绑定tv_result控件
    public TextView tv_insertUser1;

    @BindView(R.id.tv_downLoadFile) //绑定tv_result控件
    public TextView tv_downLoadFile;

    @BindView(R.id.tv_insertUser2) //绑定tv_result控件
    public TextView tv_insertUser2;

    @BindView(R.id.tv_upload) //绑定tv_result控件
    public TextView tv_upload;

    @BindView(R.id.tv_multiUpload) //绑定tv_result控件
    public TextView tv_multiUpload;

    @BindView(R.id.tv_multiUpload1) //绑定tv_result控件
    public TextView tv_multiUpload1;

    @BindView(R.id.tv_multiUpload2) //绑定tv_result控件
    public TextView tv_multiUpload2;


    private String id;
    private Map<String, Object> map;
    int pageNo = 0;

    private User user;

    private String userId, userName;

    private FilePresenter filePresenter;

    private final static String TAG = MainActivity1.class.getSimpleName();
    ArrayList<ImageItem> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePresenter = new FilePresenter();
        filePresenter.attachView(this);
        setContentView(R.layout.activity_main1);

        ButterKnife.bind(this);

//        http://www.huihemuchang.com/pasture-app/commodity/getCommodityData?subType=&type=011001&userId=U00001836&classType=1
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        Log.i("codecraeer", "getFilesDir = " + getFilesDir());
        Log.i("codecraeer", "getExternalFilesDir = " + getExternalFilesDir("exter_test").getAbsolutePath());
        Log.i("codecraeer", "getDownloadCacheDirectory = " + Environment.getDownloadCacheDirectory().getAbsolutePath());
        Log.i("codecraeer", "getDataDirectory = " + Environment.getDataDirectory().getAbsolutePath());
        Log.i("codecraeer", "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.i("codecraeer", "getExternalStoragePublicDirectory = " + Environment.getExternalStoragePublicDirectory("pub_test"));
    }

    @OnClick({R.id.tv_getUserList, R.id.tv_getUserResultByUserId, R.id.tv_getUsersByPage, R.id.tv_result3, R.id.tv_insertUser4,
            R.id.tv_insertUser1, R.id.tv_downLoadFile, R.id.tv_insertUser2, R.id.tv_upload, R.id.tv_multiUpload,
            R.id.tv_result10, R.id.tv_result12, R.id.tv_multiUpload1, R.id.tv_multiUpload2})   //给 tv_result 设置一个点击事件
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.tv_getUserList:
                getUserList();
                break;
            case R.id.tv_getUserResultByUserId:
                id = "18311004536";
                getUserResultByUserId();
                break;
            case R.id.tv_getUsersByPage:
                pageNo++;
                map = new HashMap<String, Object>();
                map.put("pageSize", 4);
                map.put("pageNo", pageNo + "");
                getUsersByPage();
                break;
            case R.id.tv_result3:
                user = new User();
                user.setUserId("123456");
                user.setUserName("白胡子");
                insert();
                break;
            case R.id.tv_insertUser4:
                pageNo++;
                user = new User();
                user.setUserId("123456789" + pageNo);
                user.setUserName("波尼"+ pageNo);
                user.setAge(20+ pageNo);
                insertUser();
                break;

            case R.id.tv_insertUser1:
                pageNo++;
                userId = "0123" + pageNo;
                userName = "zzh" + pageNo;
                insertUser1();
                break;
            case R.id.tv_downLoadFile:
                downLoadFile();
                break;

            case R.id.tv_insertUser2:
                pageNo++;
                map = new HashMap<String, Object>();
                map.put("userId", vcode());
                map.put("userName", "黑胡子蒂奇" + pageNo);
                map.put("age", pageNo);
                insertUser2();
                break;
            case R.id.tv_upload:
                upload();
                break;
            case R.id.tv_multiUpload:
                multiUpload();
                break;
            case R.id.tv_result10:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, 1);
                selectPic();
                break;
            case R.id.tv_result12:
                test();
                break;
            case R.id.tv_multiUpload1:
                multiUpload1();
                break;
            case R.id.tv_multiUpload2:
                multiUpload2();
                break;


        }
    }

    private void getUserList() {
        Subscribe.getUserList(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void getUserResultByUserId() {
        Subscribe.getUserResultByUserId(id, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void getUsersByPage() {
        Subscribe.getUsersByPage(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void insert() {
//        Subscribe.insert(user, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
//            @Override
//            public void onSuccess(String result) {
//                //成功
//                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
//                ResultInfo resultInfo = GsonUtils.fromJson(result,
//                        ResultInfo.class);
//                tv_result.setText(result);
//            }
//
//            @Override
//            public void onFault(String errorMsg) {
//                //失败
//                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
//            }
//        }, MainActivity1.this));
    }

    private void insertUser() {
        Subscribe.insertUser(user, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    // form
    private void insertUser1() {
        Subscribe.insertUser1(userId, userName, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(result);
                    }
                });

            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void insertUser2() {
        Subscribe.insertUser2(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void downLoadFile(){
//        String url = "http://www.huihemuchang.com/nfs/apk/app-release.apk";
        String url = "http://192.168.188.184:8080/images/20191118151548225/d09996c6-4f61-4b08-b482-d6ac8eb450bd.jpg";

        String path = "";
        String dir = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {// 检查是否有存储卡
            dir = Environment.getExternalStorageDirectory() + "/android-test/";
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
        path = dir + "huiheapp-debug.apk";
        path = dir + "test.jpg";
        filePresenter.downLoadFile(url, path);
    }

    private void upload(){
        String path = images.get(0).path;
        Log.i("--path--", "--path--" + path);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Subscribe.upload(part, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "上传成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "上传失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void multiUpload(){
//        String path = images.get(0).path;
//        Log.i("--path--", "--path--" + path);
//        File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        MultipartBody.Part[] parts = new MultipartBody.Part[images.size()];
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i).path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            parts[i] = filePart;
        }

        Subscribe.multiUpload(parts, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "上传成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "上传失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void multiUpload1(){
        List<Integer> typeList = new ArrayList<>();
//        typeList.add(1);
//        typeList.add(0);

//        MultipartBody.Part[] parts = new MultipartBody.Part[images.size()];
        List<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i).path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            parts[i] = filePart;
            parts.add(filePart);

            typeList.add(1);
        }
        String json_str = JSON.toJSONString(typeList);
        Log.i("--json_str--", "--json_str--" + json_str);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("application/json"), json_str);
//        RequestBody requestBody4 = toRequestBody(json_str);

        String userId = "18311004536";
        RequestBody userId_requestBody = toRequestBody(userId);
        Log.i("--userId--", "--userId--" + userId);
        String content = "的路嘻嘻嘻！";
        RequestBody content_requestBody = toRequestBody(content);

        Subscribe.multiUpload1(userId_requestBody, content_requestBody, requestBody3, parts,  new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "上传成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "上传失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void multiUpload2(){
//        MultipartBody.Part[] parts = new MultipartBody.Part[images.size()];
        List<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i).path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            parts[i] = filePart;
            parts.add(filePart);
        }
//        RequestBody requestBody3 = RequestBody.create(MediaType.parse("application/json"), json_str);
//        RequestBody requestBody4 = toRequestBody(json_str);

        String userId = "18311004536";
        RequestBody userId_requestBody = toRequestBody(userId);
        Log.i("--userId--", "--userId--" + userId);
        String content = "哈哈哈哈！";
        RequestBody content_requestBody = toRequestBody(content);
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("userId", userId_requestBody);
        map.put("content", content_requestBody);

        Subscribe.multiUpload2(map, parts,  new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "上传成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "上传失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void test() {
        List<User> typeList = new ArrayList<User>();
        User user = new User();
        user.setUserId("123");
        user.setUserName("哈哈");
        user.setPhoneNum("1235555");
        user.setSex("男");
        typeList.add(user);

        User user1 = new User();
        user1.setUserId("122223");
        user1.setUserName("嘻嘻");
        user1.setPhoneNum("11111");
        user1.setSex("女");
        typeList.add(user1);

        String json_str = JSON.toJSONString(typeList);
//        RequestBody requestBody3 = RequestBody.create(MediaType.parse("application/json"), json_str);
        RequestBody requestBody3 = toRequestBody(json_str);

        RequestBody requestBody_username = toRequestBody("boss");
        RequestBody requestBody_name = toRequestBody("big boss");
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("typeList", requestBody3);
        map.put("userName", requestBody_username);
        map.put("name", requestBody_name);
//        map.put("pageNo", "2");
//        RequestBody requestBody4 = toRequestBody(json_str);
        RequestBody requestBody_age = toRequestBody("12");
        Subscribe.test("12", map,  new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }
    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    @Override
    public void onSuccess(File file) {
        Toast.makeText(MainActivity1.this, "下载成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(MainActivity1.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        filePresenter.detachView();
        super.onDestroy();
    }

    private void selectPic(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.i("--images--", "--images--" + images.size());
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class PicassoImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

            Glide.with(activity).load(Uri.fromFile(new File(path))).into(imageView);

        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }

        @Override
        public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {

        }
    }
    /**
     * 生成6位随机数验证码
     * @return
     */
    public String vcode(){
        String vcode = "";
        for (int i = 0; i < 11; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }

}
