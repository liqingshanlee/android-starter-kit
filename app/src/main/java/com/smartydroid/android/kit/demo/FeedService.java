/**
 * Created by YuGang Yang on September 25, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.smartydroid.android.kit.demo;

import com.smartydroid.android.starter.kit.network.Result;
import java.util.List;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FeedService {

  @GET("api/v1/tweet/list") Call<Result<List<Tweet>>> getTweetList(
      @Query("page") int page,
      @Query("page_size") int pageSize);
}
