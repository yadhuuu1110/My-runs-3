package com.yadhuchoudhary.yadhu_choudhary_myruns3

object Constants {
    // Input Types
    const val INPUT_TYPE_MANUAL = 0
    const val INPUT_TYPE_GPS = 1
    const val INPUT_TYPE_AUTOMATIC = 2

    // Activity Types
    val ACTIVITY_TYPES = arrayOf(
        "Running",
        "Walking",
        "Standing",
        "Cycling",
        "Hiking",
        "Downhill Skiing",
        "Cross-Country Skiing",
        "Snowboarding",
        "Skating",
        "Swimming",
        "Mountain Biking",
        "Wheelchair",
        "Elliptical",
        "Other"
    )

    // Dialog IDs
    const val DIALOG_DATE = 1
    const val DIALOG_TIME = 2
    const val DIALOG_DURATION = 3
    const val DIALOG_DISTANCE = 4
    const val DIALOG_CALORIES = 5
    const val DIALOG_HEART_RATE = 6
    const val DIALOG_COMMENT = 7
    const val DIALOG_PHOTO = 8

    // Intent Keys
    const val EXTRA_ACTIVITY_TYPE = "activity_type"
    const val EXTRA_INPUT_TYPE = "input_type"
    const val EXTRA_EXERCISE_ID = "exercise_id"

    // SharedPreferences Keys
    const val PREFS_NAME = "MyRunsPrefs"
    const val PREF_UNIT_PREFERENCE = "unit_preference"
    const val PREF_PRIVACY = "privacy"
    const val PREF_WEBPAGE = "webpage"

    // Unit Preferences
    const val UNIT_METRIC = "Metric (Kilometers)"
    const val UNIT_IMPERIAL = "Imperial (Miles)"

    // Conversion Factors
    const val MILES_TO_KM = 1.60934
    const val FEET_TO_METERS = 0.3048
}