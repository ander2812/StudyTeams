package co.app.studyteam.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.databinding.MemberRowBinding
import co.app.studyteam.databinding.ScheduleRowBinding

class MembersView (itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding: MemberRowBinding = MemberRowBinding.bind(itemView)

    val name = binding.userNameText
}