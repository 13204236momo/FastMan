package com.tianshang.common.retrofit.interceptor;

import android.util.Log;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 添加公共参数的拦截器
 */
public class BaseParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equalsIgnoreCase("POST")) {
            Request.Builder requestBuilder = request.newBuilder();
            RequestBody formBody = new FormBody.Builder()
                    .add("userId", "10000")
                    .add("sessionToken", "E34343RDFDRGRT43RFERGFRE")
                    .add("q_version", "1.1")
                    .add("device_id", "android-344365")
                    .add("platform", "android")
                    .add("device_version", "6.0")
                    .add("req_timestamp", System.currentTimeMillis() + "")
                    .add("app_name", "forums")
                    .add("sign", "md5")
                    .build();
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            Log.d("zhou", postBodyString);
            request = requestBuilder
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                            postBodyString))
                    .build();
        }else {
            //TODO Get

        }

        return chain.proceed(request);
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
