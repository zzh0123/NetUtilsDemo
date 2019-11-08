package com.zzh.netutilsdemo.view;

import java.io.File;

/**
 * Author: zzhh
 * Date: 2019/8/7 14:47
 * Description: ${DESCRIPTION}
 */
public interface FileView extends MvpView {
    void onSuccess(File file);
    void showError(String message);
}
