package bills.project.billmanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import bills.project.billmanagementapp.databinding.ActivitySignUpBinding

class ActivitySignUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

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
        binding.btnLogIn.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
    }

    fun validateSignUp(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val emailAddress = binding.etEmailAddress.text.toString()
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
        if (createPassword.isEmpty()){
            binding.tilPassword.error = "Please create password"
            error = true
        }
        if (confirmPassword.isEmpty()){
            binding.tilConfirmPassword.error = "Please confirm password created"
            error = true
        }
        if (createPassword.length < 8 && createPassword.length > 12 ){
            binding.tilPassword.error = "Password must be 8 to 12 characters long"
            error = true
        }

        if (createPassword != confirmPassword) {
            binding.tilConfirmPassword.error = "Password and confirmation do not match."
            error = true
        }
        if(!error){
            Toast.makeText(this, "Signed Up Successfully", Toast.LENGTH_SHORT).show()
            finish()
//            val intent = Intent(this, PersonalInformation::class.java)
            startActivity(intent)
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