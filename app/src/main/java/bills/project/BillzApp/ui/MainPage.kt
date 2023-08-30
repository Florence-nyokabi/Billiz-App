package bills.project.BillzApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bills.project.BillzApp.R
import bills.project.BillzApp.databinding.ActivityMainPageBinding


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

    fun setupBottomNav(){
        binding.bnvHome.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.summary->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcvHome, SummaryFragment())
                        .commit()
                    true
                }
                R.id.upcoming->{
                    supportFragmentManager
                        .beginTransaction()
//                        .replace(R.id.fcvHome, UpcomingBillFragment())
                        .commit()
                    true
                }
                R.id.paid->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcvHome, PaidBillsFragment())
                        .commit()
                    true
                }

                R.id.settings->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcvHome, SettingsFragment())
                        .commit()
                    true

                }
                else->false
            }
        }
    }
}