package com.pogoda.app.ru.settings

// Logs
const val MAIN_LOG_TAG = "VVV"

// Network settings
const val WEATHER_URL_BASE = "https://api.open-meteo.com/v1/"
const val WEATHER_URL_QUERY = "forecast?"
const val PLACES_URL_BASE = "https://geocoding-api.open-meteo.com/v1/"
const val PLACES_URL_QUERY = "search?"

// Weather request settings
const val CURRENT_WEATHER =
    "temperature_2m,apparent_temperature,is_day,weather_code"
const val HOURLY_WEATHER = "temperature_2m,precipitation_probability,weather_code"
const val DAILY_WEATHER =
    "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min"
const val FORECAST_DAYS = 10