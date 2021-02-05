package com.kazeem.spinningwheel.data.service

import com.kazeem.spinningwheel.data.model.ApiModel
import retrofit2.http.GET

interface WheelService {

    @GET("539dc092-8367-414a-8892-ed3b2d666dbe")
    suspend fun getWheelData() : List<ApiModel>

}