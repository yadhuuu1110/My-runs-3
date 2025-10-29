package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.content.Context
import android.preference.PreferenceManager

object UnitConverter {

    fun isMetric(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val unitPref = prefs.getString(Constants.PREF_UNIT_PREFERENCE, Constants.UNIT_IMPERIAL)
        return unitPref == Constants.UNIT_METRIC
    }

    fun convertDistance(distanceInMiles: Double, context: Context): Double {
        return if (isMetric(context)) {
            distanceInMiles * Constants.MILES_TO_KM
        } else {
            distanceInMiles
        }
    }

    fun formatDistance(distanceInMiles: Double, context: Context): String {
        val converted = convertDistance(distanceInMiles, context)
        val unit = if (isMetric(context)) "Kilometers" else "Miles"
        return String.format("%.2f %s", converted, unit)
    }

    fun getDistanceUnit(context: Context): String {
        return if (isMetric(context)) "Kilometers" else "Miles"
    }

    fun getDistanceUnitShort(context: Context): String {
        return if (isMetric(context)) "km" else "mi"
    }

    fun convertClimb(climbInFeet: Double, context: Context): Double {
        return if (isMetric(context)) {
            climbInFeet * Constants.FEET_TO_METERS
        } else {
            climbInFeet
        }
    }

    fun formatClimb(climbInFeet: Double, context: Context): String {
        val converted = convertClimb(climbInFeet, context)
        val unit = if (isMetric(context)) "meters" else "feet"
        return String.format("%.2f %s", converted, unit)
    }

    fun formatDuration(durationInSeconds: Double): String {
        val hours = (durationInSeconds / 3600).toInt()
        val minutes = ((durationInSeconds % 3600) / 60).toInt()
        val seconds = (durationInSeconds % 60).toInt()

        return when {
            hours > 0 -> String.format("%dh %dm %ds", hours, minutes, seconds)
            minutes > 0 -> String.format("%dm %ds", minutes, seconds)
            else -> String.format("%ds", seconds)
        }
    }
}