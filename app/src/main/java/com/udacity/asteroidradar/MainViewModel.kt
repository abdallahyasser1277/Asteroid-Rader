package com.udacity.asteroidradar

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.db.AsteroidDataBase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.api.getDate
import kotlinx.coroutines.launch

class MainViewModel(val db: AsteroidDataBase,
                    application: Application
) : AndroidViewModel(application) {

    private val asteroidRpstry =
        NasaRepositry(db)

    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?> get() = _pictureOfDay

    private val _navigateToAsteroidDetails = MutableLiveData<Boolean>()
    val navigateToAsteroidDetails: LiveData<Boolean> get() = _navigateToAsteroidDetails

    lateinit var asteroidClicked: Asteroid

    private val _asteroidsList = MutableLiveData<List<Asteroid>?>()
    val asteroidsList: LiveData<List<Asteroid>?> get() = _asteroidsList

    init {
        viewModelScope.launch {
            try {
                asteroidRpstry.refreshData(getDate(), getDate(7))
                _pictureOfDay.value = asteroidRpstry.loadPictureOfDay()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
                Toast.makeText(application, "Failure: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        getTodayAsteroids()
        _navigateToAsteroidDetails.value = false
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        asteroidClicked = asteroid
        _navigateToAsteroidDetails.value=true
    }

    fun toAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = false
    }

    fun getWeekAsteroids() {
        viewModelScope.launch {
            db.asteroidDataBaseDao.getAllAsteroidsSortedByDate(getDate(), getDate(7))
                .collect {
                    _asteroidsList.value = it
                }
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            db.asteroidDataBaseDao.getAllAsteroidsSortedByDate(getDate(), getDate()).collect {
                _asteroidsList.value = it
            }
        }
    }

    fun getSavedAsteroids() {
        viewModelScope.launch {
            db.asteroidDataBaseDao.getAllAsteroids().collect {
                _asteroidsList.value = it
            }
        }
    }
}
class MainViewModelFactory(
    private val dataSource: AsteroidDataBase,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}