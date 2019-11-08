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
import com.zzh.netutilsdemo.R;
import com.zzh.netutilsdemo.bean.User;
import com.zzh.netutilsdemo.netsubscribe.Subscribe;
import com.zzh.netutilsdemo.presenter.FilePresenter;
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

    @BindView(R.id.tv_result1) //绑定tv_result控件
    public TextView tv_result1;

    @BindView(R.id.tv_result2) //绑定tv_result控件
    public TextView tv_result2;

    @BindView(R.id.tv_result3) //绑定tv_result控件
    public TextView tv_result3;

    @BindView(R.id.tv_result4) //绑定tv_result控件
    public TextView tv_result4;

    @BindView(R.id.tv_result5) //绑定tv_result控件
    public TextView tv_result5;

    @BindView(R.id.tv_result6) //绑定tv_result控件
    public TextView tv_result6;

    @BindView(R.id.tv_result11) //绑定tv_result控件
    public TextView tv_result11;

    @BindView(R.id.tv_result7) //绑定tv_result控件
    public TextView tv_result7;



    private String id;
    private Map<String, Object> map;

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
    }

    @OnClick({R.id.tv_result, R.id.tv_result1, R.id.tv_result2, R.id.tv_result3, R.id.tv_result5,
            R.id.tv_result6, R.id.tv_result11, R.id.tv_result7, R.id.tv_result8, R.id.tv_result9,
            R.id.tv_result10, R.id.tv_result12, R.id.tv_result13})   //给 tv_result 设置一个点击事件
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.tv_result:
                Toast.makeText(MainActivity1.this, "点击事件！", Toast.LENGTH_SHORT).show();
                getUsers();
                break;
            case R.id.tv_result1:
                Toast.makeText(MainActivity1.this, "点击事件1！", Toast.LENGTH_SHORT).show();
                id = "22222222";
                getUserSelectById();
                break;
            case R.id.tv_result2:
                Toast.makeText(MainActivity1.this, "点击事件2！", Toast.LENGTH_SHORT).show();
                map = new HashMap<String, Object>();
                map.put("pageSize", 5);
                map.put("pageNo", "2");
                getUsersByPage();
                break;
            case R.id.tv_result3:
                Toast.makeText(MainActivity1.this, "点击事件3！", Toast.LENGTH_SHORT).show();
                user = new User();
                user.setUserId("123456");
                user.setUserName("白胡子");
                user.setPhoneNum("123456");
                user.setSex("男");
                insert();
                break;
            case R.id.tv_result5:
                Toast.makeText(MainActivity1.this, "点击事件5！", Toast.LENGTH_SHORT).show();
                user = new User();
                user.setUserId("123456789");
                user.setUserName("波尼");
                user.setPhoneNum("123456789");
                user.setSex("女");
                insertUser();
                break;

            case R.id.tv_result6:
                Toast.makeText(MainActivity1.this, "点击事件6！", Toast.LENGTH_SHORT).show();
                userId = "0123";
                userName = "zzh";
                insertUser1();
                break;
            case R.id.tv_result11:
                downLoadFile();
                break;

            case R.id.tv_result7:
                Toast.makeText(MainActivity1.this, "点击事件7！", Toast.LENGTH_SHORT).show();
                map = new HashMap<String, Object>();
                map.put("userId", "213");
                map.put("userName", "好人");
                map.put("phoneNum", "1234");
                map.put("sex", "男");
                insertUser2();
                break;
            case R.id.tv_result8:
                upload();
                break;
            case R.id.tv_result9:
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
            case R.id.tv_result13:
                multiUpload1();
                break;

        }
    }

    private void getUsers() {
        UserSubscribe.getUsers(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void getUserSelectById() {
        UserSubscribe.getUserSelectById(id, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void getUsersByPage() {
        UserSubscribe.getUsersByPage(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void insert() {
        UserSubscribe.insert(user, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void insertUser() {
        UserSubscribe.insertUser(user, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

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
                        tv_result4.setText(result);
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
        UserSubscribe.insertUser2(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }, MainActivity1.this));
    }

    private void downLoadFile(){
        String url = "http://www.huihemuchang.com/nfs/apk/app-release.apk";
        String path = "";
        String dir = "";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {// 检查是否有存储卡
            dir = Environment.getExternalStorageDirectory() + "/test/";
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
        path = dir + "app-debug.apk";
        filePresenter.downLoadFile(url, path);
    }

    private void upload(){
        String path = images.get(0).path;
        Log.i("--path--", "--path--" + path);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        UserSubscribe.upload(part, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
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

        UserSubscribe.multiUpload(parts, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
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

        UserSubscribe.multiUpload1(userId_requestBody, content_requestBody, requestBody3, parts,  new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
            }

            @Override
            public void onFault(String errorMsg) {
                //失败
                Toast.makeText(MainActivity1.this, "请求失败：" + errorMsg, Toast.LENGTH_SHORT).show();
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
        UserSubscribe.test("12", map,  new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                //成功
                Toast.makeText(MainActivity1.this, "请求成功！", Toast.LENGTH_SHORT).show();
                ResultInfo resultInfo = GsonUtils.fromJson(result,
                        ResultInfo.class);
                tv_result4.setText(result);
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


}
