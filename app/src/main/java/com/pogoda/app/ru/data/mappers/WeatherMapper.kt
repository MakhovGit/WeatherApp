package com.pogoda.app.ru.data.mappers

import com.pogoda.app.ru.data.dto.weather.CurrentWeatherDto
import com.pogoda.app.ru.data.dto.weather.DailyWeatherDto
import com.pogoda.app.ru.data.dto.weather.HourlyWeatherDto
import com.pogoda.app.ru.data.dto.weather.WeatherDto
import com.pogoda.app.ru.model.weather.CurrentWeatherInfo
import com.pogoda.app.ru.model.weather.DailyWeatherInfo
import com.pogoda.app.ru.model.weather.HourlyWeatherInfo
import com.pogoda.app.ru.model.weather.WeatherInfo
import com.pogoda.app.ru.utils.ONE
import com.pogoda.app.ru.utils.TWO
import com.pogoda.app.ru.utils.ZERO
import com.pogoda.app.ru.utils.mappers.WeatherDescriptionMapper
import com.pogoda.app.ru.utils.mappers.WeatherIconMapper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeatherMapper(
    private val descriptionMapper: WeatherDescriptionMapper,
    private val iconMapper: WeatherIconMapper
) {

    private fun normalizeTemperature(temperature: Float, unit: String): String {
        var newTemperature = String.format(Locale.ROOT, "%.${SYMBOLS_AFTER_COMMA}f", temperature)
        if (SYMBOLS_AFTER_COMMA > Int.ZERO && newTemperature[newTemperature.length - Int.ONE] == LAST_TEMPERATURE_SYMBOL) {
            newTemperature = newTemperature.dropLast(LAST_SYMBOLS_TO_DROP)
        }
        return newTemperature + unit
    }

    private fun normalizeTime(dateTimeString: String) = dateTimeString.drop(FIRST_SYMBOLS_TO_DROP)

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat(INPUT_PATTERN, Locale.getDefault())
        val outputFormat = SimpleDateFormat(OUTPUT_PATTERN, Locale.getDefault())
        try {
            val date = inputFormat.parse(dateString) ?: Date()
            return outputFormat.format(date).replaceFirstChar { it.uppercaseChar() }
        } catch (error: Exception) {
            error.printStackTrace()
            return dateString
        }
    }

    private fun filterToday(dateString: String): Boolean {
        val calendarNow = Calendar.getInstance()
        val calendarTest = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(COMMON_DATETIME_PATTERN, Locale.ROOT)
        val dateTest = dateFormat.parse(dateString)
        calendarTest.time = dateTest ?: Date()
        return ((calendarNow.get(Calendar.DAY_OF_MONTH) == calendarTest.get(Calendar.DAY_OF_MONTH)))
                && (calendarTest.get(Calendar.HOUR_OF_DAY) >= calendarNow.get(Calendar.HOUR_OF_DAY))
    }

    private fun filterTomorrow(dateString: String): Boolean {
        val calendarTomorrow = Calendar.getInstance()
        val calendarTest = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(COMMON_DATETIME_PATTERN, Locale.ROOT)
        val dateTest = dateFormat.parse(dateString)
        calendarTest.time = dateTest ?: Date()
        calendarTomorrow.set(
            Calendar.DAY_OF_MONTH,
            calendarTomorrow.get(Calendar.DAY_OF_MONTH) + Int.ONE
        )
        return ((calendarTomorrow.get(Calendar.DAY_OF_MONTH) == calendarTest.get(Calendar.DAY_OF_MONTH)))
    }


    private fun map(
        place: String,
        currentWeather: CurrentWeatherDto,
        temperatureUnit: String,
        apparentTemperatureUnit: String
    ) = CurrentWeatherInfo(
        place = place,
        temperature = normalizeTemperature(currentWeather.temperature2m, temperatureUnit),
        apparentTemperature = normalizeTemperature(
            currentWeather.apparentTemperature, apparentTemperatureUnit
        ),
        description = descriptionMapper.map(currentWeather.weatherCode),
        icon = iconMapper.map(currentWeather.weatherCode)
    )

    private fun mapToday(
        hourlyWeather: HourlyWeatherDto,
        temperatureUnit: String,
        probabilityUnit: String
    ): List<HourlyWeatherInfo> {
        val targetList: MutableList<HourlyWeatherInfo> = mutableListOf()
        hourlyWeather.temperature2m.forEachIndexed { index, number ->
            if (filterToday(hourlyWeather.time[index])) {
                val hourlyWeatherInfo = HourlyWeatherInfo(
                    time = normalizeTime(hourlyWeather.time[index]),
                    icon = iconMapper.map(hourlyWeather.weatherCode[index]),
                    temperature = normalizeTemperature(number, temperatureUnit),
                    precipitationProbability = hourlyWeather.precipitationProbability[index],
                    probabilityUnit = probabilityUnit
                )
                targetList.add(hourlyWeatherInfo)
            }
        }
        return targetList.toList()
    }

    private fun mapTomorrow(
        hourlyWeather: HourlyWeatherDto,
        temperatureUnit: String,
        probabilityUnit: String
    ): List<HourlyWeatherInfo> {
        val targetList: MutableList<HourlyWeatherInfo> = mutableListOf()
        hourlyWeather.temperature2m.forEachIndexed { index, number ->
            if (filterTomorrow(hourlyWeather.time[index])) {
                val hourlyWeatherInfo = HourlyWeatherInfo(
                    time = normalizeTime(hourlyWeather.time[index]),
                    icon = iconMapper.map(hourlyWeather.weatherCode[index]),
                    temperature = normalizeTemperature(number, temperatureUnit),
                    precipitationProbability = hourlyWeather.precipitationProbability[index],
                    probabilityUnit = probabilityUnit
                )
                targetList.add(hourlyWeatherInfo)
            }
        }
        return targetList.toList()
    }

    private fun map(
        dailyWeather: DailyWeatherDto,
        temperatureUnit: String
    ): List<DailyWeatherInfo> {
        val targetList: MutableList<DailyWeatherInfo> = mutableListOf()
        dailyWeather.temperature2mMin.forEachIndexed { index, number ->
            val averageTemperature = (number + dailyWeather.temperature2mMax[index]) / Float.TWO
            val averageApparentTemperature =
                (dailyWeather.apparentTemperatureMin[index] + dailyWeather.apparentTemperatureMax[index]) / Float.TWO
            val dailyWeatherInfo = DailyWeatherInfo(
                date = formatDate(dailyWeather.time[index]),
                description = descriptionMapper.map(dailyWeather.weatherCode[index]),
                temperature = normalizeTemperature(averageTemperature, temperatureUnit),
                apparentTemperature = normalizeTemperature(
                    averageApparentTemperature,
                    temperatureUnit
                ),
                icon = iconMapper.map(dailyWeather.weatherCode[index])
            )
            targetList.add(dailyWeatherInfo)
        }
        return targetList.toList()
    }

    fun map(place: String, weather: WeatherDto): WeatherInfo {
        return WeatherInfo(
            currentWeatherInfo = map(
                place = place,
                currentWeather = weather.currentWeather,
                temperatureUnit = weather.currentUnits.temperature2m.first().toString(),
                apparentTemperatureUnit = weather.currentUnits.apparentTemperature.first()
                    .toString()
            ),
            todayHourlyWeatherInfo = mapToday(
                hourlyWeather = weather.hourlyWeather,
                temperatureUnit = weather.hourlyUnits.temperature2m.first().toString(),
                probabilityUnit = weather.hourlyUnits.precipitationProbability
            ),
            tomorrowHourlyWeatherInfo = mapTomorrow(
                hourlyWeather = weather.hourlyWeather,
                temperatureUnit = weather.hourlyUnits.temperature2m.first().toString(),
                probabilityUnit = weather.hourlyUnits.precipitationProbability
            ),
            dailyWeatherInfo = map(
                dailyWeather = weather.dailyWeather,
                temperatureUnit = weather.dailyUnits.temperature2mMin.first().toString()
            )
        )
    }

    companion object {
        private const val SYMBOLS_AFTER_COMMA = 0
        private const val LAST_TEMPERATURE_SYMBOL = '0'
        private const val LAST_SYMBOLS_TO_DROP = 2
        private const val FIRST_SYMBOLS_TO_DROP = 11
        private const val COMMON_DATETIME_PATTERN = "yyyy-mm-dd'T'hh:mm"
        private const val INPUT_PATTERN = "yyyy-MM-dd"
        private const val OUTPUT_PATTERN = "EEEE, MMM dd"
    }
}