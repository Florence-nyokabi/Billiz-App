package bills.project.billmanagementapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.billmanagementapp.Repository.UserRepository
import bills.project.billmanagementapp.model.RegisterRequest
import bills.project.billmanagementapp.model.RegisterResponse
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    var userRepo = UserRepository()
    val regLiveData = MutableLiveData<RegisterResponse>()
    val errLiveData = MutableLiveData<String>()

    fun registerUser(registerRequest: RegisterRequest){
        viewModelScope.launch {
            val response = userRepo.registerUser(registerRequest)
            if (response.isSuccessful){
                regLiveData.postValue(response.body())
            }
            else{
                errLiveData.postValue(response.errorBody()?.string())
            }
        }
    }
}