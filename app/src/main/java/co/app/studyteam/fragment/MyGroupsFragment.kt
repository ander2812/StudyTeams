package co.app.studyteam.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.app.studyteam.activity.MenuMain
import co.app.studyteam.activity.CreateGroupActivity
import co.app.studyteam.adapter.MyGroupAdapter
import co.app.studyteam.databinding.FragmentMyGroupsBinding
import co.app.studyteam.model.Group
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyGroupsFragment : Fragment() {

    private var _binding: FragmentMyGroupsBinding? = null
    private val binding get() = _binding!!
    private val adapter = MyGroupAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyGroupsBinding.inflate(inflater, container, false)
        val view = binding.root

        var username = (activity as? MenuMain)?.getUserName()

        var groupRecycler = binding.myGroupsRecycler
        groupRecycler.setHasFixedSize(true)
        groupRecycler.layoutManager = LinearLayoutManager(activity)
        groupRecycler.adapter = adapter

        getGroup()


        binding.btnAddGroup.setOnClickListener{

            val intent = Intent(this.context, CreateGroupActivity::class.java)
                intent.putExtra("username", (activity as? MenuMain)?.getUserName())

            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getGroup(){
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid).collection("group").orderBy("name", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            adapter.deleteAllGroup()
            for (document in task.result!!) {
                val group = document.toObject(Group::class.java)
                adapter.addGroup(group)
                adapter.notifyDataSetChanged()
            }
        }

    }



    companion object {
        @JvmStatic
        fun newInstance() = MyGroupsFragment()
    }
}