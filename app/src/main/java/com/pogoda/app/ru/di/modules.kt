package com.pogoda.app.ru.di

import com.pogoda.app.ru.ui.screens.main_screen.MainScreenReducer
import com.pogoda.app.ru.ui.screens.main_screen.MainScreenStateHolder
import com.pogoda.app.ru.ui.screens.main_screen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appKoinModule = module {
    singleOf(::MainScreenReducer)
    singleOf(::MainScreenStateHolder)
    viewModelOf(::MainScreenViewModel)
}