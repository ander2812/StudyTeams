package co.app.studyteam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.app.studyteam.model.Schedule
import co.app.studyteam.databinding.ActivityCreateGroupBinding
import co.app.studyteam.model.Group
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class CreateGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateGroupBinding
    private var username:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        username = intent.extras?.getString("username")
        Toast.makeText(this,"Hola $username", Toast.LENGTH_LONG).show()

        binding.btnCrear.setOnClickListener{
            // crear grupo --------------
            var name = binding.eTxtSubject.text.toString()
            var description = ""
            var ownerId = binding.eTxtSubject.text.toString()
            var schedule: ArrayList<Schedule> = arrayListOf()

            val group = Group(UUID.randomUUID().toString(),name, Date().toString(), ownerId, description, schedule)
            Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid).collection("group").document(group.id).set(group)
            Firebase.firestore.collection("groups").document(group.id).set(group)
            super.finish();
        }

    }
}