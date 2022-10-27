package co.app.studyteam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.app.studyteam.databinding.ActivityMainBinding
import co.app.studyteam.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var userListener: UserListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener{


            var user = binding.textUser.text.toString()
            var password = binding.textPassword.text.toString()

            Firebase.auth.signInWithEmailAndPassword(user, password).addOnSuccessListener {

                val fbuser = Firebase.auth.currentUser
                if (fbuser!!.isEmailVerified){

                    Firebase.firestore.collection("users").document(fbuser.uid).get().addOnSuccessListener {
                        val user = it.toObject(User::class.java)
                        saveUser(user!!)
                        val intent = Intent(this, MenuMain::class.java)
                        startActivity(intent)
                    }
                }

            }.addOnFailureListener {
                Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
            }

        }

        binding.btnUserCreate.setOnClickListener {
            val intent = Intent(this, UserCreateActivity::class.java)
            startActivity(intent)
        }

    }

    fun saveUser(user: User){
        val sp = getSharedPreferences("app", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user", json).apply()
    }

    interface UserListener{
        fun userName(username:String)
    }
}