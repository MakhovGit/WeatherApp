package com.pogoda.app.ru.data.interactors.location_interactor

sealed interface LocationInteractorMessages {

    sealed class RequestLocationError {
        data object LocationPermissionsNotGranted : RequestLocationError()
        data object CurrentLocationIsNull : RequestLocationError()
        data object RequestLocationFailed : RequestLocationError()
    }

    sealed class RequestAddressError {
        data object CannotFindAddress : RequestAddressError()
    }

    sealed class RequestLocation : LocationInteractorMessages {
        data object Processing : RequestLocation()
        data class Success(
            val latitude: Double,
            val longitude: Double
        ) : RequestLocation()

        data class Failure(
            val error: RequestLocationError
        ) : RequestLocation()
    }

    sealed class RequestAddress : LocationInteractorMessages {
        data object Processing : RequestAddress()
        data class Success(
            val latitude: Double,
            val longitude: Double,
            val address: String
        ) : RequestAddress()

        data class Failure(
            val latitude: Double,
            val longitude: Double,
            val error: RequestAddressError
        ) : RequestAddress()
    }
}