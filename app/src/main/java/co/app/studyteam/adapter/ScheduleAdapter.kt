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


class ScheduleAdapter(activity: ScheduleActivity): RecyclerView.Adapter<ScheduleView>() {


    private val activity = activity
    private val addSchedules = ArrayList<Schedule>()
    var startHour: Double = 0.0
    var endHour: Double = 0.0
    var day: String = ""
    private val schedules = ArrayList<ScheduleItemView>()

    private var initialSchedule: ScheduleItemView? = null
    private var finalSchedule: ScheduleItemView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleView {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.schedule_row, parent, false)
        val scheduleView = ScheduleView(view)
        return scheduleView
    }

    override fun onBindViewHolder(holder: ScheduleView, position: Int) {
        val item =schedules[position]
        holder.name.text = item.name
        holder.startHour.text =  "${item.hour}:${if(item.minute==0) "00" else item.minute}"

        if(item.selected){
            holder.backView.setBackgroundColor(Color.GREEN)
        }else{
            holder.backView.setBackgroundColor(Color.rgb(255,255,255))
        }


        holder.action.setOnClickListener {

            if(initialSchedule == null && finalSchedule == null){
                Log.e("1:", "entro aqui" )
                item.selected = true
                initialSchedule = item
                notifyDataSetChanged()
                Log.e(">>>Initial","${item.hour}:${item.minute} ${item.day}")
                activity.showAddActivityBtn()
            }else if(initialSchedule != null && finalSchedule == null){
                Log.e("2:", "entro aqui" )

                val dInitialHour = initialSchedule!!.hour + initialSchedule!!.minute/60.0
                val dFinalHour = item.hour + item.minute/60.0
                if(dFinalHour > dInitialHour){
                    finalSchedule = item
                    finalSchedule!!.selected = true
                    val init = schedules.indexOf(initialSchedule)
                    val end = schedules.indexOf(finalSchedule)
                    for(i in init..end){
                        schedules[i].selected = true
                    }
                    notifyDataSetChanged()
                    startHour = dInitialHour
                    endHour = dFinalHour
                    day = item.day
                    if(dFinalHour != null && dInitialHour != null){
                        createActivity()
                    }
                    Log.e(">>>Final","${item.hour}:${item.minute} ${item.day}")

                    initialSchedule = null
                    finalSchedule = null

                }else{
                    initialSchedule!!.selected = false
                    initialSchedule = null
                    finalSchedule = null
                    activity.hideAddActivityBtn()
                }
                notifyDataSetChanged()

            }else if(initialSchedule != null && finalSchedule != null){
                Log.e("3:", "entro aqui" )
                val init = schedules.indexOf(initialSchedule)
                val end = schedules.indexOf(finalSchedule)
                for(i in init until end){
                    schedules[i].selected = false
                }
                initialSchedule = null
                finalSchedule = null
                notifyDataSetChanged()
                activity.hideAddActivityBtn()

            }

        }

    }

    fun deleteAllGroup(){
        schedules.clear()
    }

    fun showActivities(activities: Array<Activities>, day: String){
        var num : String
        var temp : Double
        var num1 : Int
        var num2 : Int
        var start = 0
        var end = 0
                for(i in 0..33){
                    num1 = (5+(i/2))
                    num2 = (((30*i)%60)/3)/2
                    num = "$num1.$num2"
                    temp = num.toDouble()
                    schedules.add( ScheduleItemView(5+(i/2), (30*i)%60, "", day, false) )
                    for (activity in activities ){
                        for (schedule in activity.schedule){
                            if (schedule.day == day){
                                if (schedule.starthour == temp || schedule.endhour == temp && schedule.day == day){

                                    if (schedule.starthour == temp){
                                        start = i
                                    }else if( schedule.endhour == temp){
                                        end = i
                                    }

                                    for (j in start until end){

                                        schedules[j].name = activity.name

                                    }

                                }else{

                                    schedules[i].name = ""

                                }
                            }else{
                                //schedules[i].name = ""
                            }
                        }
                    }
                }
                notifyDataSetChanged()


    }

    fun addSchedule(name: String){

            val activity = Activities(UUID.randomUUID().toString(),name, addSchedules)

            Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid).collection("activities").document(activity.id).set(activity)

    }

    private fun createActivity(): ArrayList<Schedule> {

        if(day != null && startHour!=null && endHour != null){
            if (day != "" && startHour != 0.0 && endHour != 0.0){

                val schedule = Schedule(day, startHour, endHour)

                if (!existValue(addSchedules)){

                    addSchedules.add(schedule)

                }

            }



        }

        return addSchedules
    }

    private fun existValue(schedule: ArrayList<Schedule>): Boolean {
        var exist = false
        for (i in schedule.indices) {
            if (schedule[i].starthour == startHour && schedule[i].day == day) {
                    exist = true
                    break
            }
        }
        return exist
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
    var selected:Boolean

)