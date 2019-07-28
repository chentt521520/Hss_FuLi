package com.example.applibrary.httpUtils;

public interface OnHttpCallback<T> {
    void success(T result);

    void error(int code, String msg);
}
