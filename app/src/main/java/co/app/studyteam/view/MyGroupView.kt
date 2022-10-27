package co.app.studyteam.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.R
import co.app.studyteam.model.Group

class MyGroupView (itemView: View) : RecyclerView.ViewHolder(itemView) {

    var group: Group? = null

    var name: TextView =itemView.findViewById(R.id.subjectText)
    var day: TextView =  itemView.findViewById(R.id.dateText)
    var starthour: TextView = itemView.findViewById(R.id.startHourText)
    var finishhour: TextView = itemView.findViewById(R.id.finishHourText)

    fun initializer(groupView: Group){
        name.text = groupView.name
        for (group in groupView.schedule){
            day.text = group.day
            starthour.text = group.starthour.toString()
            finishhour.text = group.endhour.toString()
        }
    }
}