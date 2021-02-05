package com.kazeem.spinningwheel.data.repository

import com.kazeem.spinningwheel.data.datasource.WheelDataSource
import com.kazeem.spinningwheel.domain.mapper.mapToDomain
import com.kazeem.spinningwheel.domain.model.ApiModel
import com.kazeem.spinningwheel.domain.repository.WheelRepository

class WheelRepositoryImpl(
    private val wheelDataSource: WheelDataSource
) : WheelRepository {

    override suspend fun getWheelData(): List<ApiModel> {
        return wheelDataSource.getWheelData().map { it.mapToDomain() }
    }

}