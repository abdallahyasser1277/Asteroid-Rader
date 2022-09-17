package com.udacity.asteroidradar.db

import android.content.Context
import androidx.room.*
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg manyAsteroids: Asteroid)
    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<Asteroid>>
    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAllAsteroidsSortedByDate(startDate: String, endDate: String): Flow<List<Asteroid>>
    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :date")
    fun deleteOldAsteroids(date: String)
}

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {

    abstract val asteroidDataBaseDao: AsteroidDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDataBase? = null
        fun getInstance(context: Context): AsteroidDataBase {
            synchronized(this) {
                var instnc = INSTANCE

                if (instnc == null) {
                    instnc = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDataBase::class.java,
                        "asteroid_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instnc
                }
                return instnc
            }
        }
    }
}
