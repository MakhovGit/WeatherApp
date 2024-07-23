package com.pogoda.app.ru.data.interactors.location_interactor

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import com.pogoda.app.ru.utils.COMMA
import com.pogoda.app.ru.utils.ONE
import com.pogoda.app.ru.utils.SPACE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Locale

class LocationInteractor(
    private val app: Application
) {
    private val _outFlow: MutableSharedFlow<LocationInteractorMessages> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "Location interactor error!: ${error.message.toString()}")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private var requestLocationJob: Job? = null
    private var successLocationJob: Job? = null
    private var failureLocationJob: Job? = null
    private var requestAddressJob: Job? = null
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
    private val locationPriority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
    private val cancellationTokenSource = CancellationTokenSource()
    private val geocoder = Geocoder(app, Locale.getDefault())

    private fun isGooglePlayServicesAvailable(app: Application) =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(app) ==
                com.google.android.gms.common.ConnectionResult.SUCCESS

    private fun isLocationPermissionGranted(context: Context) =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private suspend fun onLocationNotNull(location: Location) {
        Log.d(MAIN_LOG_TAG, "LocationInteractor: requestLocation() success!")
        _outFlow.emit(
            LocationInteractorMessages.RequestLocation.Success(
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    private suspend fun onLocationNull() {
        Log.e(
            MAIN_LOG_TAG,
            "LocationInteractor: requestLocation() failed! Location is NULL!"
        )
        _outFlow.emit(
            LocationInteractorMessages.RequestLocation.Failure(
                error = LocationInteractorMessages
                    .RequestLocationError.CurrentLocationIsNull
            )
        )
    }

    private fun onLocationSuccess(location: Location?) {
        successLocationJob?.cancel()
        successLocationJob = mainScope.launch {
            if (location != null) {
                onLocationNotNull(location)
            } else {
                onLocationNull()
            }
        }
    }

    private fun onLocationFailure(exception: Exception) {
        failureLocationJob?.cancel()
        failureLocationJob = mainScope.launch {
            Log.e(MAIN_LOG_TAG, "LocationInteractor: requestLocation() failed! $exception")
            _outFlow.emit(
                LocationInteractorMessages.RequestLocation.Failure(
                    error = LocationInteractorMessages.RequestLocationError.RequestLocationFailed
                )
            )
        }
    }

    private suspend fun onGooglePlayServicesNotAvailable() {
        Log.e(
            MAIN_LOG_TAG,
            "LocationInteractor: requestLocation() failed! Google Play services not available!"
        )
        _outFlow.emit(
            LocationInteractorMessages.RequestLocation.Failure(
                error = LocationInteractorMessages
                    .RequestLocationError.GooglePlayServicesNotAvailable
            )
        )
    }

    private suspend fun onLocationPermissionNotGranted() {
        Log.e(
            MAIN_LOG_TAG,
            "LocationInteractor: requestLocation() failed! Location permissions not granted!"
        )
        _outFlow.emit(
            LocationInteractorMessages.RequestLocation.Failure(
                error = LocationInteractorMessages
                    .RequestLocationError.LocationPermissionsNotGranted
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(
            locationPriority,
            cancellationTokenSource.token
        )
            .addOnSuccessListener { location: Location? ->
                onLocationSuccess(location)
            }
            .addOnFailureListener { exception ->
                onLocationFailure(exception)
            }
            .addOnCanceledListener {
                Log.d(MAIN_LOG_TAG, "LocationInteractor: requestLocation() cancelled!")
            }
            .addOnCompleteListener {
                Log.d(MAIN_LOG_TAG, "LocationInteractor: requestLocation() completed!")
            }

    }

    private suspend fun onAddressesNull(latitude: Double, longitude: Double) {
        Log.e(MAIN_LOG_TAG, "LocationInteractor: requestAddress() failed!")
        _outFlow.emit(
            LocationInteractorMessages.RequestAddress.Failure(
                latitude = latitude,
                longitude = longitude,
                error = LocationInteractorMessages.RequestAddressError.CannotFindAddress
            )
        )
    }

    private suspend fun onAddressesNotNull(
        latitude: Double,
        longitude: Double,
        addresses: List<Address>
    ) {
        Log.d(MAIN_LOG_TAG, "LocationInteractor: requestAddress() success!")
        _outFlow.emit(
            LocationInteractorMessages.RequestAddress.Success(
                latitude = latitude,
                longitude = longitude,
                address = buildString {
                    append(addresses.first().locality)
                    append(String.COMMA)
                    append(String.SPACE)
                    append(addresses.first().countryName)
                }
            )
        )
    }

    private suspend fun checkPermissions() {
        if (isLocationPermissionGranted(app)) {
            getCurrentLocation()
        } else {
            onLocationPermissionNotGranted()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocation() {
        requestLocationJob?.cancel()
        requestLocationJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "LocationInteractor: requestLocation() launched!")
            _outFlow.emit(LocationInteractorMessages.RequestLocation.Processing)
            if (isGooglePlayServicesAvailable(app)) {
                checkPermissions()
            } else {
                onGooglePlayServicesNotAvailable()
            }
        }
    }

    fun requestAddress(latitude: Double, longitude: Double) {
        requestAddressJob?.cancel()
        requestAddressJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "LocationInteractor: requestAddress() launched!")
            _outFlow.emit(LocationInteractorMessages.RequestAddress.Processing)
            val addresses = geocoder.getFromLocation(latitude, longitude, Int.ONE)
            if (addresses != null && addresses.first() != null) {
                onAddressesNotNull(latitude, longitude, addresses)
            } else {
                onAddressesNull(latitude, longitude)
            }
        }
    }

}