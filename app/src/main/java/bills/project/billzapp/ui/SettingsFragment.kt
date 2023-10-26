package bills.project.billzapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import bills.project.billzapp.BuildConfig
import bills.project.billzapp.ViewModel.BillzViewModel
import bills.project.billzapp.databinding.FragmentSettingsBinding


import bills.project.billzapp.utils.Constants
import com.google.android.material.snackbar.Snackbar


class SettingsFragment : Fragment() {
    var binding: FragmentSettingsBinding? = null
    val billsviewModel: BillzViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        binding?.tvAppVersion?.text = "APP VERSION : ${BuildConfig.VERSION_NAME}"
        binding?.tvLogOut?.setOnClickListener { logOut() }
        binding?.tvSync?.setOnClickListener { syncData() }
    }

    fun logOut(){
        val sharedPrefs = requireContext().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(Constants.USER_ID, Constants.EMPTY_STRING)
        editor.putString(Constants.ACCESS_TOKEN, Constants.EMPTY_STRING)
        editor.apply()
        requireContext().startActivity(Intent(requireContext(), ActivityLogin::class.java))
    }
    fun syncData() {
        // TODO: Detect network connectivity here if needed

        binding?.pbSync?.visibility = View.VISIBLE
        billsviewModel.fetchRemoteBills()

        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                val progress = (10000 - p0).toInt()
                binding?.pbSync?.progress = progress
            }

            override fun onFinish() {
                binding?.pbSync?.visibility = View.GONE
                Snackbar.make(binding!!.settingRoot, "Sync Completed", Snackbar.LENGTH_SHORT).show()
            }
        }

        timer.start()
    }
}