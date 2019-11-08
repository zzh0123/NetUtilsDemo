package com.zzh.netutilsdemo.presenter;

import com.zzh.netutilsdemo.netsubscribe.Subscribe;
import com.zzh.netutilsdemo.netutils.FileObsever;
import com.zzh.netutilsdemo.view.FileView;

import java.io.File;

/**
 * Author: zzhh
 * Date: 2019/8/7 15:16
 * Description: ${DESCRIPTION}
 */
public class FilePresenter implements Presenter<FileView>{
    private FileView fileView;
    @Override
    public void attachView(FileView view) {
        this.fileView = view;
    }

    @Override
    public void detachView() {
        if (fileView != null) fileView =null;
    }

    public void downLoadFile(String url, final String path){
        Subscribe.downLoadFile(url, new FileObsever(path) {
            @Override
            public void onSuccess(File file) {
                if (file != null && file.exists()) {
                    fileView.onSuccess(file);
                } else {
                    fileView.showError("file is null");
                }
            }

            @Override
            public void onErrorMsg(String msg) {

            }
        });
    }
}
