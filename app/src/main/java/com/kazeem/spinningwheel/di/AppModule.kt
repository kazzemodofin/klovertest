package com.kazeem.spinningwheel.di

import android.app.Application
import android.content.Context
import com.kazeem.spinningwheel.presentation.MainActivity
import com.kazeem.spinningwheel.presentation.fragments.GameFragment
import com.kazeem.spinningwheel.presentation.fragments.MainFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun gameFragment(): GameFragment

}