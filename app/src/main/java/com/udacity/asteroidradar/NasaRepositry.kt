package com.udacity.asteroidradar

import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.db.AsteroidDataBase
import com.udacity.asteroidradar.api.asDataModel
import com.udacity.asteroidradar.api.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NasaRepositry(private val db: AsteroidDataBase) {

    suspend fun refreshData(startDate: String = getDate(), endDate: String = getDate() ) {
        withContext(Dispatchers.IO) {
            val response =
                AsteroidApi.retrofitService.getNeatEarthObjectsAsync(startDate, endDate).await()
            val asteroidList = parseAsteroidsJsonResult(JSONObject(response.string()))
            db.asteroidDataBaseDao.insertAll(*asteroidList.asDataModel())
        }
    }

    suspend fun loadPictureOfDay(): PictureOfDay? {
        var picOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            picOfDay = AsteroidApi.retrofitService.getPictureOfDayAsync().await()
        }
        return if (picOfDay.mediaType == "image") {
            picOfDay
        } else {
            null
        }
    }

    suspend fun deleteOldData() {
        withContext(Dispatchers.IO) {
            db.asteroidDataBaseDao.deleteOldAsteroids(getDate())
        }
    }
}