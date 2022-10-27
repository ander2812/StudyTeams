package co.app.studyteam.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.R
import co.app.studyteam.model.Group

class GroupView(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var group: Group? = null

    var name: TextView =itemView.findViewById(R.id.groupSubject)
    var day: TextView =  itemView.findViewById(R.id.groupDate)
    var groupButton: TextView = itemView.findViewById(R.id.groupButton)
    var button: Button = itemView.findViewById(R.id.button)
    var starthour: TextView = itemView.findViewById(R.id.startHour)
    var finishhour: TextView = itemView.findViewById(R.id.finishHour)


    fun initializer(groupView: Group){
        name.text = groupView.name
        for (group in groupView.schedule){
            day.text = group.day
            starthour.text = group.starthour.toString()
            finishhour.text = group.endhour.toString()
        }
    }


}