package bills.project.billmanagementapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import bills.project.billmanagementapp.ViewModel.UserViewModel
import bills.project.billmanagementapp.databinding.ActivitySignUpBinding
import bills.project.billmanagementapp.model.RegisterRequest

class ActivitySignUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    override fun onResume() {
        super.onResume()
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            clearErrors()
            validateSignUp()
        }
        userViewModel.errLiveData.observe(this, Observer { err->
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
            binding.pbRegister.visibility = View.GONE
        })
        userViewModel.regLiveData.observe(this, Observer { regResponse->
            binding.pbRegister.visibility = View.GONE
            Toast.makeText(this, regResponse.message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ActivityLogin::class.java))
        })

        binding.btnLogIn.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
    }

    fun validateSignUp(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val emailAddress = binding.etEmailAddress.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val createPassword = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        var error  = false

        if (firstName.isEmpty()){
            binding.tilFirstName.error = "Please input first name"
            error = true
        }
        if (lastName.isEmpty()) {
            binding.tilLastName.error = "Please input last name"
            error = true
        }
        if(emailAddress.isEmpty()){
            binding.tilEmailAddress.error = "Please input email address"
            error = true
        }
        if(phoneNumber.isEmpty()){
            binding.tilPhoneNumber.error = "Please input phone number"
            error = true
        }
        if (createPassword.isEmpty()){
            binding.tilPassword.error = "Please create password"
            error = true
        }
        if (confirmPassword.isEmpty()){
            binding.tilConfirmPassword.error = "Please confirm password created"
            error = true
        }
        if (createPassword.length < 8  || createPassword.length > 12 ){
            binding.tilPassword.error = "Password must be 8 to 12 characters long"
            error = true
        }

        if (createPassword != confirmPassword) {
            binding.tilConfirmPassword.error = "Password and confirmation do not match."
            error = true
        }
        if(!error){
            val registerRequest = RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                email = emailAddress,
                password = createPassword,
                phoneNumber = phoneNumber
            )
            binding.pbRegister.visibility = View.VISIBLE
            userViewModel.registerUser(registerRequest)
        }
    }
    fun clearErrors(){
        binding.tilFirstName.error = null
        binding.tilLastName.error = null
        binding.tilEmailAddress.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
    }
}