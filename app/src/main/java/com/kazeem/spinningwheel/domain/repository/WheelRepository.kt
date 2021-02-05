package com.kazeem.spinningwheel.domain.repository

import com.kazeem.spinningwheel.domain.model.ApiModel

interface WheelRepository {

    suspend fun getWheelData() : List<ApiModel>

}