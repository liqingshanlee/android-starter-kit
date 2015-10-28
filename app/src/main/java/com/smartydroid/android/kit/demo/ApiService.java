/**
 * Created by YuGang Yang on September 25, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.smartydroid.android.kit.demo;

import com.smartydroid.android.starter.kit.retrofit.TimberLoggingInterceptor;
import com.squareup.okhttp.OkHttpClient;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class ApiService {

  private static final String sBaseUrl = "http://123.57.78.45";
  private Retrofit mRetrofit;

  // Make this class a thread safe singleton
  private static class SingletonHolder {
    private static final ApiService INSTANCE = new ApiService();
  }

  public static synchronized ApiService get() {
    return SingletonHolder.INSTANCE;
  }

  protected Retrofit.Builder newRetrofitBuilder() {
    return new Retrofit.Builder();
  }

  private Retrofit retrofit() {
    if (mRetrofit == null) {
      Retrofit.Builder builder = newRetrofitBuilder();
      OkHttpClient client = new OkHttpClient();
      client.interceptors().add(new TimberLoggingInterceptor());
      mRetrofit = builder.baseUrl(sBaseUrl)
          .addConverterFactory(JacksonConverterFactory.create())
          .client(client)
          .build();
    }

    return mRetrofit;
  }

  public static NewsService createNewsService() {
    return get().retrofit().create(NewsService.class);
  }
}
