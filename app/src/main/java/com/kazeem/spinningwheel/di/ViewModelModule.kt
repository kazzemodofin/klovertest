package com.kazeem.spinningwheel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kazeem.spinningwheel.presentation.viewModel.WheelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WheelViewModel::class)
    abstract fun bindViewModel(viewModel: WheelViewModel): ViewModel

}