package com.pogoda.app.ru.di

import com.pogoda.app.ru.data.facades.main_facade.MainFacade
import com.pogoda.app.ru.data.interactors.location_interactor.LocationInteractor
import com.pogoda.app.ru.data.interactors.network_interactor.NetworkInteractor
import com.pogoda.app.ru.data.mappers.PlacesMapper
import com.pogoda.app.ru.data.mappers.WeatherMapper
import com.pogoda.app.ru.data.repository.NetworkRepository
import com.pogoda.app.ru.data.repository.NetworkRepositoryImpl
import com.pogoda.app.ru.data.retrofit.places.RetrofitPlacesDatasource
import com.pogoda.app.ru.data.retrofit.weather.RetrofitWeatherDatasource
import com.pogoda.app.ru.ui.screens.main_screen.MainScreenReducer
import com.pogoda.app.ru.ui.screens.main_screen.MainScreenStateHolder
import com.pogoda.app.ru.ui.screens.main_screen.MainScreenViewModel
import com.pogoda.app.ru.utils.mappers.WeatherDescriptionMapper
import com.pogoda.app.ru.utils.mappers.WeatherIconMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appKoinModule = module {
    singleOf(::RetrofitWeatherDatasource)
    singleOf(::RetrofitPlacesDatasource)
    single<NetworkRepository> {
        NetworkRepositoryImpl(
            retrofitWeatherDatasource = get(),
            retrofitPlacesDatasource = get()
        )
    }
    singleOf(::WeatherDescriptionMapper)
    singleOf(::WeatherIconMapper)
    singleOf(::WeatherMapper)
    singleOf(::PlacesMapper)
    singleOf(::LocationInteractor)
    singleOf(::NetworkInteractor)
    singleOf(::MainFacade)
    singleOf(::MainScreenReducer)
    singleOf(::MainScreenStateHolder)
    viewModelOf(::MainScreenViewModel)
}