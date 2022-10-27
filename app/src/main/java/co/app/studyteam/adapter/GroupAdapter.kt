package co.app.studyteam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.activity.DetailGroupsActivity
import co.app.studyteam.model.GroupMembers
import co.app.studyteam.view.GroupView
import co.app.studyteam.R
import co.app.studyteam.model.Group
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class GroupAdapter(): RecyclerView.Adapter<GroupView>() {

    private val groups = ArrayList<Group>()
    private var groupHolder: GroupView? = null

    fun addGroup(group: Group){
        groups.add(group)
        notifyItemInserted(groups.size-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.group_row, parent, false)
        val groupView = GroupView(view)
        return groupView
    }

    override fun onBindViewHolder(holder: GroupView, position: Int) {
        val chart =groups[position]
        holder.group = chart
        groupHolder = holder

        holder.initializer(chart)
        // verificar vista -------------------------------------------------------

        holder.button.setOnClickListener {
            joinGroup(holder)
        }

        holder.groupButton.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailGroupsActivity::class.java)

            val group = Group(holder.group!!.id, holder.group!!.name,holder.group!!.creationDate,holder.group!!.description, holder.group!!.description, holder.group!!.schedule)

            intent.putExtra("group", group)

            holder.itemView.context.startActivity(intent)

        }

    }

    fun deleteAllGroup(){
        groups.clear()
    }

    fun convertLongToTime(time: Long, format:String): String {
        val date = Date(time)
        //"yyyy.MM.dd HH:mm"
        val stringformat = SimpleDateFormat(format)
        return stringformat.format(date)
    }

    private fun joinGroup(holder: GroupView){

        val user = GroupMembers(Firebase.auth.currentUser!!.uid)

        Firebase.firestore.collection("groups").document(holder.group!!.id).collection("groupMembers").document(Firebase.auth.currentUser!!.uid).set(user)

    }

    override fun getItemCount(): Int {
        return groups.size
    }
}