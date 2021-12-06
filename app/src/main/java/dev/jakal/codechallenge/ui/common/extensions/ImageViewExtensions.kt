package dev.jakal.codechallenge.ui.common.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import dev.jakal.codechallenge.R

fun ImageView.bindPhoto(context: Context, imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .fitCenter()
        .placeholder(R.drawable.ic_image_placeholder_black_48dp)
        .into(this)
}
