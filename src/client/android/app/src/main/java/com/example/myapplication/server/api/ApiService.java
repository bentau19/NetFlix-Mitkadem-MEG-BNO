package com.example.myapplication.server.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ApiService {
    @GET
    Call<Object> get(@Url String url, @HeaderMap Map<String, String> headers);

    @POST
    Call<Object> post(@Url String url, @HeaderMap Map<String, String> headers, @Body Object body);

    @PUT
    Call<Object> put(@Url String url, @HeaderMap Map<String, String> headers, @Body Object body);

    @DELETE
    Call<Object> delete(@Url String url, @HeaderMap Map<String, String> headers);
}
