package com.kazeem.spinningwheel.data.datasource

import com.kazeem.spinningwheel.data.model.ApiModel

interface WheelDataSource {

    suspend fun getWheelData() : List<ApiModel>

}