package com.essnn.shouyinji.utils;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RxWeatherService {
    @GET("weather_mini")
    Observable<WeatherEntity> getMessage(@Query("city") String city);
}
