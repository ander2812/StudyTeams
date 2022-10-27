package co.app.studyteam.model

data class Activities(
    var id: String = "",
    var name: String = "",
    var schedule: ArrayList<Schedule> = arrayListOf()
)