package com.phalder.nasa.asteroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {
    @Query("select * from asteroid_table order by closeApproachDate desc")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("select * from asteroid_table " +
                    "where closeApproachDate >= :startDate and  closeApproachDate <= :endDate " +
                        "order by closeApproachDate desc")
    fun getAllSortedAsteroids(startDate: String, endDate: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: Asteroid)

    @Query("delete from asteroid_table where closeApproachDate < :selectedDate")
    fun deleteRecords(selectedDate: String)
}