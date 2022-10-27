package co.app.studyteam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import co.app.studyteam.dialog.DialogName
import co.app.studyteam.adapter.ScheduleAdapter
import co.app.studyteam.databinding.ActivityScheduleBinding
import co.app.studyteam.model.Activities
import co.app.studyteam.model.Course
import com.google.gson.Gson

class ScheduleActivity : AppCompatActivity() {

    val binding: ActivityScheduleBinding by lazy {
        ActivityScheduleBinding.inflate(layoutInflater)
    }

    private val adapter: ScheduleAdapter by lazy {
        ScheduleAdapter(this)
    }

    private lateinit var courses:Array<Course>
    private lateinit var activities:Array<Activities>
    private var days: Array<String> = arrayOf("LU", "MA", "MI", "JU", "VI", "SA")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jsonCourses = intent.extras?.getString("courses")
        var sum = 0
        val jsonActivities = intent.extras?.getString("activities")

        courses = Gson().fromJson(jsonCourses, Array<Course>::class.java)
        activities = Gson().fromJson(jsonActivities, Array<Activities>::class.java)


        println(courses[0].name)
        println(activities[0].name)



        binding.scheduleRecycler.layoutManager = LinearLayoutManager(this)
        binding.scheduleRecycler.setHasFixedSize(true)
        binding.scheduleRecycler.adapter = adapter



            binding.dateScheduleText.text = days[sum]
            adapter.showActivities(activities, days[sum])


            binding.rowNextButton.setOnClickListener {

                adapter.deleteAllGroup()

                sum = (sum+1)%6

                binding.dateScheduleText.text = days[sum]
                adapter.showActivities(activities, days[sum])

            }

            binding.addActivityBtn.setOnClickListener {

                DialogName(
                    showText = { text ->



                    },
                    onSubmitClickListener = { name ->

                                adapter.addSchedule(name)
                                finish()


                    }
                ).show(supportFragmentManager,"dialog")

            }

            binding.rowBackButton.setOnClickListener {

                adapter.deleteAllGroup()

                sum--
                if(sum == -1) sum = 5


                binding.dateScheduleText.text = days[sum]
                adapter.showActivities(activities, days[sum])
            }




    }

    fun showAddActivityBtn() {
        binding.addActivityBtn.visibility = VISIBLE
    }

    fun hideAddActivityBtn() {
        binding.addActivityBtn.visibility = GONE
    }
}