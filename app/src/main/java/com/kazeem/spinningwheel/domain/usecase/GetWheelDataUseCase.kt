package com.kazeem.spinningwheel.domain.usecase

import com.kazeem.spinningwheel.domain.model.ApiModel
import com.kazeem.spinningwheel.domain.repository.WheelRepository
import com.kazeem.spinningwheel.utils.ResultWrapper
import com.kazeem.spinningwheel.utils.saferApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetWheelDataUseCase @Inject constructor(
    private val repository: WheelRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun invoke(): ResultWrapper<List<ApiModel>> {
        return saferApiCall(dispatcher) {
            repository.getWheelData()
        }
    }
}