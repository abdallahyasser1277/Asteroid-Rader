package com.udacity.asteroidradar

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.udacity.asteroidradar.db.AsteroidDataBase
import com.udacity.asteroidradar.db.ToDoItemDataBaseDao
import com.udacity.asteroidradar.model.Asteroid
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest {

    private lateinit var asteroidDao: ToDoItemDataBaseDao
    private lateinit var db: AsteroidDataBase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDataBase::class.java)

            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        asteroidDao = db.asteroidDataBaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAsteroid() {

        val steroidOne = Asteroid(100, "test", "test", 0.0, 0.0, 0.0, 0.0, false)
        asteroidDao.insert(steroidOne)

        val steroidTwo = asteroidDao.get(100)
        assertEquals(steroidOne, steroidTwo)

    }
}