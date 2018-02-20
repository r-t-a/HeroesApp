package com.ryan.heroestopbuilds.Interface;

import com.ryan.heroestopbuilds.Models.Heroes;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HeroesApi {
    @GET("heroes/")
    Call<List<Heroes>> getAllHeroes();
}
