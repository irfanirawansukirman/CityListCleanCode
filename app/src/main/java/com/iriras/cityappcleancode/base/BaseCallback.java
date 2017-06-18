package com.iriras.cityappcleancode.base;

import com.iriras.cityappcleancode.api.network.NetworkError;

/**
 * Created by irfan on 6/17/17.
 */

public interface BaseCallback<T> {
    void onSuccess(T data);
    void onError(NetworkError error);
}
