package com.ryan.heroestopbuilds;

import android.support.annotation.NonNull;

import com.ryan.heroestopbuilds.Interface.HeroesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum Rest {
    INSTANCE;
    public final static String DOMAIN = "http://hotsapi.net/api/v1/";
    private final HeroesApi api;
    Rest() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(HeroesApi.class);
    }
    public @NonNull
    HeroesApi api() {
        return api;
    }
}
