package co.app.studyteam.model

import java.io.Serializable
import java.util.*

class Group ( var id: String = "",
              var name: String = "",
              var creationDate: String = "",
              var description: String = "",
              var owner_id: String = "",
              var schedule: ArrayList<Schedule> = arrayListOf()
              //var username: String
              ):Serializable