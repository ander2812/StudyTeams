package co.app.studyteam.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.app.studyteam.R
import co.app.studyteam.model.Schedule
import co.app.studyteam.activity.ScheduleActivity
import co.app.studyteam.view.ScheduleView
import co.app.studyteam.model.Activities
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class ScheduleAdapter(activity: ScheduleActivity) : RecyclerView.Adapter<ScheduleView>() {


    private val activity = activity
    private val addSchedules = hashMapOf<String, ArrayList<Schedule>>()
    var day: String = "LU"
    private val schedules = ArrayList<ScheduleItemView>()


    init {
        addSchedules["LU"] = arrayListOf()
        addSchedules["MA"] = arrayListOf()
        addSchedules["MI"] = arrayListOf()
        addSchedules["JU"] = arrayListOf()
        addSchedules["VI"] = arrayListOf()
        addSchedules["SA"] = arrayListOf()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.schedule_row, parent, false)
        val scheduleView = ScheduleView(view)
        return scheduleView
    }

    override fun onBindViewHolder(holder: ScheduleView, position: Int) {
        val item = schedules[position]
        holder.name.text = item.name
        holder.startHour.text = "${item.hour}:${if (item.minute == 0) "00" else item.minute}"

        if (item.selected) {
            holder.backView.setBackgroundColor(Color.GREEN)
        } else {
            holder.backView.setBackgroundColor(Color.rgb(255, 255, 255))
        }


        holder.action.setOnClickListener {
            if (item.name.isEmpty()) {
                item.selected = !item.selected
            }
            notifyDataSetChanged()

            calculateSchedules()

        }

    }

    fun calculateSchedules() {
        addSchedules[day]?.clear()
        var intial = 0.0
        var end = 0.0
        var currentLapse = false
        for (sche in schedules) {

            if (sche.selected && !currentLapse) {
                intial = sche.hour + sche.minute / 60.0
                currentLapse = true
                continue
            }
            if (currentLapse && !sche.selected) {
                end = sche.hour + sche.minute / 60.0
                createActivity(day, intial, end);
                currentLapse = false
                continue
            }
            if (currentLapse && sche.selected && schedules.last() == sche) {
                end = (sche.hour + sche.minute / 60.0) + 0.5
                createActivity(day, intial, end);
                currentLapse = false
                continue
            }

        }
        if(getAllSchedule().size == 0){
            activity.hideAddActivityBtn()
        }else{
            activity.showAddActivityBtn()
        }
    }

    private fun createActivity(day: String, inital: Double, end: Double) {

        if (inital != 0.0 && end != 0.0) {
            val schedule = Schedule(day, inital, end)
            addSchedules[day]?.add(schedule)
        }

    }


    fun deleteAllGroup() {
        schedules.clear()
    }

    fun showActivities(activities: Array<Activities>, day: String) {
        this.day = day
        var num: String
        var temp: Double
        var num1: Int
        var num2: Int
        var start = 0
        var end = 0
        for (i in 0..33) {
            num1 = (5 + (i / 2))
            num2 = (((30 * i) % 60) / 3) / 2
            num = "$num1.$num2"
            temp = num.toDouble()
            schedules.add(ScheduleItemView(5 + (i / 2), (30 * i) % 60, "", day, false))
            for (activity in activities) {
                for (schedule in activity.schedule) {
                    if (schedule.day == day) {
                        if (schedule.startHour == temp || schedule.endHour == temp && schedule.day == day) {

                            if (schedule.startHour == temp) {
                                start = i
                            } else if (schedule.endHour == temp) {
                                end = i
                            }

                            for (j in start until end) {

                                schedules[j].name = activity.name

                            }

                        } else {

                            schedules[i].name = ""

                        }
                    } else {
                        //schedules[i].name = ""
                    }
                }
            }
        }
        notifyDataSetChanged()


    }

    fun addSchedule(name: String) {

        val data = getAllSchedule()

        val activity = Activities(UUID.randomUUID().toString(), name,data)
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid)
            .collection("activities").document(activity.id).set(activity)
    }

    private fun getAllSchedule():ArrayList<Schedule> {
        val output = arrayListOf<Schedule>()
        for(value in addSchedules.values){
            output.addAll(value)
        }
        return output
    }


    override fun getItemCount(): Int {
        return schedules.size
    }
}

data class ScheduleItemView(
    val hour: Int,
    val minute: Int,
    var name: String,
    var day: String,
    var selected: Boolean

)