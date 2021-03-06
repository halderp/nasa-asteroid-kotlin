package com.phalder.nasa.asteroid

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.phalder.nasa.asteroid.database.Asteroid


// This is the binding adaptor for Displaying "Image of the day" using Glide.
// The default placeholder and error image is packaged in the app resources for better user experience
// First Type of Binding of ImageView
@BindingAdapter("apodImage")
fun bindApodImage(apodImageView: ImageView, apodImgUrl: String?) {
    apodImgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(apodImageView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.default_apod)
                .error(R.drawable.default_apod))
            .into(apodImageView)
    }
}
// This is the binding adaptor for Displaying Status Image of Asteroid whether it is hazardous or not.
// Second Type of Binding of ImageView
@BindingAdapter("neoStatusImage")
fun ImageView.bindNeoStatusImage(item: Asteroid?) {
    if (item != null) {
        if (item.isPotentiallyHazardous == true)
            setImageResource(R.drawable.ic_status_potentially_hazardous)
        else
            setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidImage")
fun bindAsteroidImage(asteroidImgView: ImageView, item: Asteroid?) {
    if (item != null) {
        if (item.isPotentiallyHazardous == true)
            asteroidImgView.setImageResource(R.drawable.asteroid_hazardous)
        else
            asteroidImgView.setImageResource(R.drawable.asteroid_safe)
    }
}

