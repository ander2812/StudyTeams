package co.app.studyteam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.app.studyteam.model.GroupMembers
import co.app.studyteam.adapter.MembersAdapter
import co.app.studyteam.databinding.ActivityMembersBinding
import co.app.studyteam.model.Group
import co.app.studyteam.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MembersActivity : AppCompatActivity() {

    private val members = arrayListOf<GroupMembers>()
    private var user: User? = null
    private lateinit var binding: ActivityMembersBinding
    private val adapter: MembersAdapter by lazy {
        MembersAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var membersRecycler = binding.membersRecycler
        membersRecycler.setHasFixedSize(true)
        membersRecycler.layoutManager = LinearLayoutManager(this)
        membersRecycler.adapter = adapter

        val group = intent.extras?.getSerializable("group") as Group

        getMembers(group)

        binding.rowMembersButton.setOnClickListener {
            val intent = Intent(this, DetailGroupsActivity::class.java)

            startActivity(intent)
        }
    }

    private fun getMembers(group: Group){

        lifecycleScope.launch(Dispatchers.IO) {
            val task = Firebase.firestore.collection("groups").document(group.id)
                .collection("groupMembers").get().await()

            for (document in task.documents) {
                val member = document.toObject(GroupMembers::class.java)
                member?.let {
                    members.add(it)


                }

            }

            for (member in members) {
                val task = Firebase.firestore.collection("users").whereEqualTo("id", member.id).get().await()

                adapter.deleteAllMembers()

                for (document in task) {
                    user = document.toObject(User::class.java)
                }
            }

            adapter.addMembers(user!!)
            adapter.notifyDataSetChanged()
        }


    }
}