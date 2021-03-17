package com.resultados.loto.lotonicaragua

import android.graphics.Typeface
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


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
    //val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_in)
    //this.startAnimation(animation)
}

fun View.scaleOut(){
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_out)
    this.startAnimation(animation)
}


fun Chart<*>.setupBarChartStyle(labels:List<String>){
    val mChart = this as BarChart

    val black75 = ContextCompat.getColor(context, R.color.md_black_1000_75)
    val bgColor = ContextCompat.getColor(context, R.color.md_grey_100)
    val typeFaceBold = try { ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold)}catch (e:Exception){ Typeface.DEFAULT}
    //val typeFaceRegular = ResourcesCompat.getFont(context, R.font.source_sans_pro)
    with(mChart){
        setNoDataText(context.getString(R.string.no_data_chart))
        setNoDataTextColor(black75)

        description.isEnabled = false
        setNoDataTextTypeface(typeFaceBold)
        setExtraOffsets(5f, 15f, 5f, 10f);
        setBackgroundColor(bgColor)
        setDrawBorders(false)
        setTouchEnabled(true)
        //setVisibleXRange(0f,5f)
        //setVisibleXRangeMaximum(5f)
        animateX(400)
        //setFitBars(true)
        //marker = markerView
        animateXY(400, 0);
        setDrawGridBackground(false)
        with(xAxis){
            valueFormatter = IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawLabels(true)
            textColor = black75
            axisLineColor = black75
            //spaceMax = 2f
            //granularity = 1f
            //isGranularityEnabled = true
            typeface = typeFaceBold
            axisMinimum = -0.5f
        }
        with(axisLeft){
            setDrawGridLines(false)
            axisMinimum = 0f
            axisLeft.typeface = typeFaceBold
            setDrawGridLines(true)
            spaceTop = 10f
        }
        axisRight.isEnabled = false
    }
}
