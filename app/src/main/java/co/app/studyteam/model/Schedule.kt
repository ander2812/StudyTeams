package co.app.studyteam.model

import java.io.Serializable

data class Schedule (
    var day: String = "",
    var starthour: Double = 0.0,
    var endhour: Double = 0.0
        ): Serializable