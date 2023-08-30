
package bills.project.BillzApp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import bills.project.BillzApp.databinding.ActivityLoginBinding
import bills.project.BillzApp.ViewModel.LoginUserViewModel
import bills.project.BillzApp.model.LoginRequest
import bills.project.BillzApp.model.LoginResponse
import bills.project.BillzApp.utils.Constants


class ActivityLogin : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val loginUserViewModel: LoginUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setContentView(binding.root)

        binding.btnLoggingIn.setOnClickListener {
            clearLogInErrors()
            validateLogIn()
        }
        loginUserViewModel.errLiveData.observe(this, Observer { err->
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
            binding.pbLogin.visibility = View.GONE
        })
        loginUserViewModel.regLiveData.observe(this, Observer { logResponse->
            persistLogin(logResponse)
            binding.pbLogin.visibility = View.GONE
            Toast.makeText(this, logResponse.message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainPage::class.java))
            finish()
        })
    }

    fun validateLogIn(){
        val emailAddress = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        var error = false

        if (emailAddress.isBlank()){
            binding.tilEmail.error = "Please input user name"
            error = true
        }
        if (password.isBlank()){
            binding.tilPassword.error = "Please input password"
            error = true
        }
        if(!error){
            val loginRequest = LoginRequest(
                email = emailAddress,
                password = password
            )
            binding.pbLogin.visibility = View.VISIBLE
            loginUserViewModel.loginUser(loginRequest)
        }
    }

    fun clearLogInErrors(){
        binding.tilEmail.error = null
        binding.tilPassword.error = null
    }

    fun persistLogin(loginResponse: LoginResponse){
        val sharedPrefs = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(Constants.USER_ID, loginResponse.userId)
        editor.putString(Constants.ACCESS_TOKEN, loginResponse.accessToken)
        editor.apply()
    }

}
