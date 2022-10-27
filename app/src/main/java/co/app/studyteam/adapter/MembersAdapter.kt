package co.app.studyteam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.activity.MembersActivity
import co.app.studyteam.view.MembersView
import co.app.studyteam.R
import co.app.studyteam.model.User
import java.util.ArrayList

class MembersAdapter (activity: MembersActivity): RecyclerView.Adapter<MembersView>() {

    private val members = ArrayList<User>()

    fun addMembers(member: User){
        members.add(member)
        notifyItemInserted(members.size-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.member_row, parent, false)
        val membersView = MembersView(view)
        return membersView
    }

    override fun onBindViewHolder(holder: MembersView, position: Int) {
        val item = members[position]
        holder.name.text = item.name
    }

    fun deleteAllMembers(){
        members.clear()
    }

    override fun getItemCount(): Int {
        return members.size
    }
}