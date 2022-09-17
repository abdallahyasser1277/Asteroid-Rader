package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.db.AsteroidDataBase
import com.udacity.asteroidradar.NasaRepositry
import retrofit2.HttpException

class DeleteWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "DeleteDataWorker"
    }

    override suspend fun doWork(): Result {
        val db = AsteroidDataBase.getInstance(applicationContext)
        val repository = NasaRepositry(db)
        return try {
            repository.deleteOldData()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}