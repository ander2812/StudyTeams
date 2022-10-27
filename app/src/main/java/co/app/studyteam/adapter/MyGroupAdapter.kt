package co.app.studyteam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.view.MyGroupView
import co.app.studyteam.R
import co.app.studyteam.model.Group

class MyGroupAdapter: RecyclerView.Adapter<MyGroupView>() {

    private val groups = ArrayList<Group>()

    fun addGroup(group: Group){
        groups.add(group)
        notifyItemInserted(groups.size-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGroupView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.mygroup_row, parent, false)
        val groupView = MyGroupView(view)
        return groupView
    }

    override fun onBindViewHolder(holder: MyGroupView, position: Int) {
        val chart =groups[position]
        holder.group = chart
        holder.initializer(chart)
    }

    fun deleteAllGroup(){
        groups.clear()
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}