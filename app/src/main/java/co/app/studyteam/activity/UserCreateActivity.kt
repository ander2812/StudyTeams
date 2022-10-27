package co.app.studyteam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.app.studyteam.databinding.ActivityUserCreateBinding
import co.app.studyteam.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCreateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bntUserCreate.setOnClickListener(::register)
    }

    fun register(view: View){
        Firebase.auth.createUserWithEmailAndPassword(
            binding.eTxtEmail.text.toString(),

            binding.eTxtPassword.text.toString()
        ).addOnSuccessListener {
            val id = Firebase.auth.currentUser?.uid
            var name = binding.eTxtName.text.toString()
            var email = binding.eTxtEmail.text.toString()
            var username = binding.eTxtUsername.text.toString()


            val user = User(id!!,name, email, username)
            Firebase.firestore.collection("users").document(user.id).set(user).addOnSuccessListener {
                sendVerificactionEmail()
                finish()
            }

        }.addOnFailureListener {
            Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
        }

        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
        finish()
        super.finish();
    }

    fun sendVerificactionEmail(){
        Firebase.auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {

            Toast.makeText(this,"Verifique su email antes de ingresar", Toast.LENGTH_LONG).show()

        }?.addOnFailureListener {

            Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()

        }
    }
}