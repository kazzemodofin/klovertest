package com.kazeem.spinningwheel.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kazeem.spinningwheel.domain.model.ApiModel
import com.kazeem.spinningwheel.domain.usecase.GetWheelDataUseCase
import com.kazeem.spinningwheel.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WheelViewModel @Inject constructor(
    private val getWheelDataUseCase: GetWheelDataUseCase
) : ViewModel() {

   fun getWheelData(): LiveData<List<ApiModel>> = liveData(Dispatchers.IO) {
        when (val result = getWheelDataUseCase.invoke()) {
            is ResultWrapper.NetworkError -> {
               Timber.d("NetworkError: ${result.cause?.localizedMessage}")
                //emit(listOf() as List<ApiModel>)
            }
            is ResultWrapper.GenericError -> {
                Timber.d("GenericError: ${result.error?.errorDescription}")
                //emit(emptyList())
            }
            is ResultWrapper.Success -> {
                Timber.d("Success")
                emit(result.value)
            }
        }
    }

}