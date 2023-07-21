package bills.project.billmanagementapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bills.project.billmanagementapp.R
import bills.project.billmanagementapp.databinding.ActivityMainBinding
import bills.project.billmanagementapp.databinding.ActivityMainPageBinding

class MainPage : AppCompatActivity() {
    lateinit var binding: ActivityMainPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setContentView(binding.root)
    }
}