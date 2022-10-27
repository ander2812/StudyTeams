package co.app.studyteam.model

data class Course (
    var id: String = "",
    var name: String = "",
    var schedule: ArrayList<Schedule> = arrayListOf()
        )