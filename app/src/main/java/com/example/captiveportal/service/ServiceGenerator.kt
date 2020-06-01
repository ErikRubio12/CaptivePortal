package com.example.captiveportal.service

import com.example.captiveportal.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.CAPTIVE_PORTAL_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}