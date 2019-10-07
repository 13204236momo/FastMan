package com.tianshang.common.Retrofit;

import com.tianshang.common.entity.app.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface NetRequest {

    @Headers({
            "authoration:apicode",
            "apicode:05cf4107e9394e77a85ce3a56300e7e5"
    })
    @GET("apis/dst/collegeInfoQuery/collegeInfoQuery?name=%E5%8C%97%E4%BA%AC")
    Observable<Result> getCall();
}
