package com.phalder.nasa.asteroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {
    @Query("select * from asteroid_table")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("select * from asteroid_table order by closeApproachDate desc")
    fun getAllSortedAsteroids(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: Asteroid)
}