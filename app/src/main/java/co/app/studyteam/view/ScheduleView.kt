package co.app.studyteam.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.databinding.ScheduleRowBinding

class ScheduleView(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding:ScheduleRowBinding = ScheduleRowBinding.bind(itemView)

    val startHour = binding.startHourScheduleText
    val name = binding.nameScheduleText
    val action = binding.scheduleBtn
    val backView = binding.backView

}

