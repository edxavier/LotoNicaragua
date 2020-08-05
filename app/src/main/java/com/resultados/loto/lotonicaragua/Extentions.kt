package com.resultados.loto.lotonicaragua

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils


fun View.setHidden(){this.visibility = View.GONE}

fun View.setVisible(){this.visibility = View.VISIBLE}

fun View.fadeIn(){
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    this.startAnimation(animation)
}

fun View.fadeOut(){
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    this.startAnimation(animation)
}

fun View.scaleIn(){
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in)
    this.startAnimation(animation)
}

fun View.scaleOut(){
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out)
    this.startAnimation(animation)
}

