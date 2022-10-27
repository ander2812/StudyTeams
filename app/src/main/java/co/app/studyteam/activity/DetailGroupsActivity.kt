package co.app.studyteam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.app.studyteam.model.GroupMembers
import co.app.studyteam.databinding.ActivityDetailGroupsBinding
import co.app.studyteam.model.Group
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailGroupsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGroupsBinding
    var hour = ""
    private val members = arrayListOf<GroupMembers>()
    var sum = 0
    var day = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGroupsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val group = intent.extras?.getSerializable("group") as Group

        binding.descriptionText.text = group.description
        binding.groupNameText.text = group.name
        getMembersQuantity(group)
        binding.dateGroupText.text = group.creationDate
        for (i in group.schedule.indices){
            hour = group.schedule[i].startHour.toString()
            day = group.schedule[i].day
        }

        binding.hourText.text = hour
        binding.dayText.text = day

        binding.membersButton.setOnClickListener {

            val intent = Intent(this, MembersActivity::class.java)

            val group = Group(group!!.id, group!!.name, group!!.creationDate, group!!.description, group!!.description, group!!.schedule)

            intent.putExtra("group", group)

            startActivity(intent)

        }

    }

    fun getMembersQuantity(group: Group){

        Firebase.firestore.collection("groups").document(group.id).collection("groupMembers").get().addOnCompleteListener { task ->

            for (document in task.result) {
                val member = document.toObject(GroupMembers::class.java)
                addMembers(member)
            }
        }
    }

    fun addMembers(member: GroupMembers){
        members.add(member)
        binding.membersText.text = members.size.toString()
    }
}