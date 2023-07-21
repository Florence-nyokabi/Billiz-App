package bills.project.billmanagementapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bills.project.billmanagementapp.R
import bills.project.billmanagementapp.databinding.ActivityMainBinding

class MainPage : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setContentView(binding.root)
    }
}