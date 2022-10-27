package co.app.studyteam.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import co.app.studyteam.activity.MenuMain
import co.app.studyteam.activity.MainActivity
import co.app.studyteam.activity.ScheduleActivity
import co.app.studyteam.adapter.ActivitiesAdapter
import co.app.studyteam.databinding.FragmentProfileBinding
import co.app.studyteam.model.Activities
import co.app.studyteam.model.Course
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var nameActivity: String? = null
    private val binding get() = _binding!!

    private val courses = arrayListOf<Course>()
    private val activities = arrayListOf<Activities>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val vista = binding.root

        var username = (activity as? MenuMain)?.getUserName()
        var name = (activity as? MenuMain)?.getName()
        //Toast.makeText(this.context,"Hola $username bienvenido a tu perfil", Toast.LENGTH_LONG).show()

        binding.txtNme.text = name

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this.context, MainActivity::class.java)

            startActivity(intent)
        }

        binding.addActivitiesButton.setOnClickListener {
            val intent = Intent(context, ScheduleActivity::class.java)
            intent.putExtra("courses", Gson().toJson(courses))
            intent.putExtra("activities", Gson().toJson(activities))

            startActivity(intent)
        }



        return vista
    }


    override fun onResume() {
        super.onResume()
        binding.coursesContainer.removeAllViews()
        binding.activitiesContainer.removeAllViews()

        getCourses()
        getActivities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getCourses() {

        Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid)
            .collection("courses").get().addOnCompleteListener { task ->
            for (document in task.result!!) {
                var course = document.toObject(Course::class.java)
                courses.add(course)
                val view = ActivitiesAdapter(this).createView(course)
                binding.coursesContainer.addView(view)
            }
        }

    }

    fun getActivities() {

        lifecycleScope.launch(Dispatchers.IO) {
            val task = Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid)
                .collection("activities").get().await()

            for (document in task.documents) {
                var activity = document.toObject(Activities::class.java)
                activity?.let {
                    activities.add(it)
                    withContext(Dispatchers.Main){
                        val view = ActivitiesAdapter(this@ProfileFragment).createView(it)
                        binding.activitiesContainer.addView(view)
                    }
                }

            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}