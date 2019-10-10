package com.tianshang.common.retrofit;

import com.tianshang.common.retrofit.result.CommonListResult;
import com.tianshang.common.entity.app.School;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RetrofitRequest {

    @Headers({
            "authoration:apicode",
            "apicode:05cf4107e9394e77a85ce3a56300e7e5"
    })
    @GET(RetrofitApi.TEST)
    Observable<CommonListResult<School>> getCall();
}
