package com.phalder.nasa.asteroid

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


// This is the binding adaptor for Displaying "Image of the day" using Glide.
// The default placeholder and error image is packaged in the app resources for better user experience
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
