package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_a_potentially_hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_not_a_potentially_hazardous_asteroid)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_a_potentially_hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.resources.getString(R.string.this_is_not_a_potentially_hazardous_asteroid)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.contentDescription =
        String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
    textView.contentDescription = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
    textView.contentDescription =
        String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        val imgUri = pictureOfDay.url.toUri().buildUpon().scheme("https").build()
        Picasso.with(imgView.context).load(imgUri).centerCrop().fit().into(imgView)

        imgView.contentDescription = String.format(
            imgView.context.resources.getString(R.string.nasa_picture_of_day_content_description_format),
            pictureOfDay.title
        )
    }
}

@BindingAdapter("bindContentDescription")
fun bindAsteroidItemDescription(view: ConstraintLayout, asteroid: Asteroid) {
    view.contentDescription = String.format(
        view.context.resources.getString(R.string.this_is_s_double_click_top_open),
        asteroid.codename
    )
}