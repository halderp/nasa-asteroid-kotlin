package com.phalder.nasa.asteroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores Asteroids information.
 * And a global method to get access to the database.
 */

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    /**
     * Define a companion object, this allows us to add functions on the AsteroidDatabase class.
     *
     * For example, clients can call `AsteroidDatabase.getInstance(context)` to instantiate
     * a new AsteroidDatabase.
     */
    companion object{
        @Volatile
        private lateinit var INSTANCE: AsteroidDatabase

        /**
         * Helper function to get the database.
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         */
        fun getInstance(context: Context): AsteroidDatabase{
            synchronized(this){

                if (!::INSTANCE.isInitialized)
                    INSTANCE = Room.databaseBuilder(
                                    context.applicationContext,
                                    AsteroidDatabase::class.java,
                                    "asteroid_database"
                                 ).build()
            }

            return INSTANCE
        }
    }
}
