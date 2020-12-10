package com.phalder.nasa.asteroid.asteroiddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phalder.nasa.asteroid.network.Asteroid

class AsteroidDetailViewModelFactory(private val asteroid:Asteroid): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidDetailViewModel::class.java)) {
            return AsteroidDetailViewModel(asteroid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}