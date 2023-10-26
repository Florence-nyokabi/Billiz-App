package bills.project.billzapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import bills.project.billzapp.R
import bills.project.billzapp.ViewModel.BillzViewModel
import bills.project.billzapp.databinding.ActivityMainPageBinding


class MainPage : AppCompatActivity() {
    lateinit var binding: ActivityMainPageBinding
    val billzViewModel: BillzViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setContentView(binding.root)
        setupBottomNav()
        billzViewModel.createUpcomingBills()
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
                        .replace(R.id.fcvHome, UpcomingBillsFragment())
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