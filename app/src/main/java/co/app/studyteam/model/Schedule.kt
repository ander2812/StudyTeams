package co.app.studyteam.model

import java.io.Serializable

data class Schedule (
    var day: String = "",
    var startHour: Double = 0.0,
    var endHour: Double = 0.0
        ): Serializable