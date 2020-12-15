package com.phalder.nasa.asteroid.asteroiddetail

import androidx.lifecycle.ViewModel
import com.phalder.nasa.asteroid.database.Asteroid

class AsteroidDetailViewModel (val asteroid: Asteroid): ViewModel() {

    lateinit var absmagnitudeString:String
    lateinit var estdiameterStr:String
    lateinit var relativeVelocityStr:String
    lateinit var distanceFromEarthStr :String


    init {
        absmagnitudeString = asteroid.absoluteMagnitude.toString() + " au"
        estdiameterStr = asteroid.estimatedDiameter.toString() + " km"
        relativeVelocityStr = asteroid.relativeVelocity.toString() + " km/sec"
        distanceFromEarthStr = asteroid.distanceFromEarth.toString() + " au"
    }
}