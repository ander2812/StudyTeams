package co.app.studyteam.adapter

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import co.app.studyteam.fragment.ProfileFragment
import co.app.studyteam.databinding.CoursesViewBinding
import co.app.studyteam.model.Activities
import co.app.studyteam.model.Course

class ActivitiesAdapter(activity: ProfileFragment) {

    private val activity = activity
    private val layoutInflater = activity.layoutInflater

    fun createView(course: Course): View {
        val metrics = activity.resources.displayMetrics
        val d = metrics.density
        val binding = CoursesViewBinding.inflate(layoutInflater, null, false)
        val view = binding.root
        binding.nameActivityText.text = course.name
        binding.editButtonActivity.setOnClickListener {

        }

        binding.deleteButtonAtivity.setOnClickListener {

        }
        val params = LinearLayout.LayoutParams(MATCH_PARENT, (120*d).toInt() )
        view.layoutParams = params
        return view
    }

    fun createView(activities: Activities): View {
        val metrics = activity.resources.displayMetrics
        val d = metrics.density
        val binding = CoursesViewBinding.inflate(layoutInflater, null, false)
        val view = binding.root
        binding.nameActivityText.text = activities.name
        binding.editButtonActivity.setOnClickListener {

        }

        binding.deleteButtonAtivity.setOnClickListener {

        }
        val params = LinearLayout.LayoutParams(MATCH_PARENT, (120*d).toInt() )
        view.layoutParams = params
        return view
    }
}