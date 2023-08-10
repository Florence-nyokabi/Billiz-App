package bills.project.billmanagementapp.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bills.project.billmanagementapp.databinding.ActivityMainBinding
import bills.project.billmanagementapp.utils.Constants
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        redirectUser()
    }

    override fun onResume() {
        super.onResume()
        Picasso
            .get()
            .load("https://res.cloudinary.com/dyxt6pqtx/image/upload/v1689854622/A4_-_1_1_aarbto.jpg")
            .fit()
            .centerCrop()
            .into(binding.backgroundImageView)

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, ActivitySignUp::class.java)
            startActivity(intent)
        }
    }

    fun redirectUser(){
        val sharedPrefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val userId = sharedPrefs.getString(Constants.USER_ID, Constants.EMPTY_STRING)
        if (userId.isNullOrBlank()){
            startActivity(Intent(this, ActivityLogin::class.java))
        }else{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}