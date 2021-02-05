package com.kazeem.spinningwheel.data.datasource

import com.kazeem.spinningwheel.data.model.ApiModel
import com.kazeem.spinningwheel.data.service.WheelService

class WheelDataSourceImpl(
    private val wheelService: WheelService
) : WheelDataSource {

    override suspend fun getWheelData(): List<ApiModel> {
        return wheelService.getWheelData()
    }

}