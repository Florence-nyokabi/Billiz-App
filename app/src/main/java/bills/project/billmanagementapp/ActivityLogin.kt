
package bills.project.billmanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import bills.project.billmanagementapp.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
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
    }

    fun validateLogIn(){
        val userName = binding.etUserName.text.toString()
        val password = binding.etPassword.text.toString()

        var error = false

        if (userName.isEmpty()){
            binding.tilUserName.error = "Please input user name"
            error = true
        }
        if (password.isEmpty()){
            binding.tilPassword.error = "Please input password"
            error = true
        }
        if(!error){
            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
            finish()
//            val intent = Intent(this, ActivityHome::class.java)
            startActivity(intent)
        }
    }

    fun clearLogInErrors(){
        binding.tilUserName.error = null
        binding.tilPassword.error = null
    }
}