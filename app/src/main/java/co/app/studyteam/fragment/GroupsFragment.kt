package co.app.studyteam.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.app.studyteam.activity.MenuMain
import co.app.studyteam.adapter.GroupAdapter
import co.app.studyteam.databinding.FragmentGroupsBinding
import co.app.studyteam.model.Group
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupsFragment(groupAdapter: Companion) : Fragment() {

    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private val adapter = GroupAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        val view = binding.root

        var username = (activity as? MenuMain)?.getUserName()

        //recrear estado
        var groupRecycler = binding.groupRecycler
        groupRecycler.setHasFixedSize(true)
        groupRecycler.layoutManager = LinearLayoutManager(activity)
        groupRecycler.adapter = adapter

        getGroup(username)

        binding.btnSearch.setOnClickListener(::searchGroup)

        return view
    }

    private fun getGroup(username: String?){
            Firebase.firestore.collection("groups").orderBy("name", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
                adapter.deleteAllGroup()
                for (document in task.result!!) {
                    val group = document.toObject(Group::class.java)
                    adapter.addGroup(group)
                    adapter.notifyDataSetChanged()
                }
            }

    }

    private fun searchGroup(view:View){
        var subject = binding.eTxtSubject.text.toString()


        lifecycleScope.launch(Dispatchers.IO) {

            val query = Firebase.firestore.collection("groups").whereEqualTo("name",subject)
            query.get().addOnCompleteListener { task->

                for( document in task.result){
                    Log.e(">>>>>>>>>>>>>>>>>>>", document.data.toString())
                }

                if (task.result?.size()!=null){

                    lateinit var groupSearch : Group
                    adapter.deleteAllGroup()

                    for (document in task.result!!){
                        groupSearch = document.toObject(Group::class.java)
                        adapter.addGroup(groupSearch)
                        adapter.notifyDataSetChanged()
                        break
                    }
                }else{

                    Toast.makeText(context,"El grupo no existe", Toast.LENGTH_LONG).show()

                    adapter.deleteAllGroup()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        getGroup((activity as? MenuMain)?.getUserName().toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() = GroupsFragment(this)
    }
}