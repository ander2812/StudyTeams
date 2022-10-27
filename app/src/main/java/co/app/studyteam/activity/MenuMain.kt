package co.app.studyteam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.app.studyteam.R
import co.app.studyteam.databinding.ActivityMenuMainBinding
import co.app.studyteam.fragment.GroupsFragment
import co.app.studyteam.fragment.MyGroupsFragment
import co.app.studyteam.fragment.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuMain : AppCompatActivity() {

    private lateinit var binding: ActivityMenuMainBinding

    private lateinit var groupsFragment: GroupsFragment
    private lateinit var profileFragment: ProfileFragment
    private var username:String? = null
    private var name:String? = null
    private var email:String? = null
    private lateinit var myGroupsFragment: MyGroupsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(Firebase.auth.currentUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            return
        }




        binding = ActivityMenuMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        groupsFragment = GroupsFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()
        username = intent.extras?.getString("username")
        name = intent.extras?.getString("name")
        email = intent.extras?.getString("email")
        myGroupsFragment = MyGroupsFragment.newInstance()

        binding.btnNav.setOnItemSelectedListener { menuapp ->
            if (menuapp.itemId == R.id.groupItem) {
                showFragment(groupsFragment)
            } else if (menuapp.itemId == R.id.profileItem) {
                showFragment(profileFragment)
            } else if (menuapp.itemId == R.id.myGroupsItem) {
                showFragment(myGroupsFragment)
            }
            true
        }
    }

    fun getUserName(): String? {

        return username

    }

    fun getName(): String? {

        return name

    }

    fun getEmail(): String? {

        return email

    }
    fun showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}