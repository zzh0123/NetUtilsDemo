package com.zzh.netutilsdemo.netutils;

/**
 *
 */
public interface OnSuccessAndFaultListener {
    void onSuccess(String result);

    void onFault(String errorMsg);
}
