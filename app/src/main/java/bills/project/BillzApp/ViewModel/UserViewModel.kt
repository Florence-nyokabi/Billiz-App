package bills.project.BillzApp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.BillzApp.Repository.UserRepository
import bills.project.BillzApp.model.RegisterRequest
import bills.project.BillzApp.model.RegisterResponse
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