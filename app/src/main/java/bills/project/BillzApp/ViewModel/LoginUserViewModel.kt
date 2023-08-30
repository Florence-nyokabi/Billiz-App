package bills.project.BillzApp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bills.project.BillzApp.Repository.LoginUserRepository
import bills.project.BillzApp.model.LoginRequest
import bills.project.BillzApp.model.LoginResponse
import kotlinx.coroutines.launch


class LoginUserViewModel: ViewModel() {
    var userLoginRepo = LoginUserRepository()
    val regLiveData = MutableLiveData<LoginResponse>()
    val errLiveData = MutableLiveData<String>()

    fun loginUser(loginRequest: LoginRequest){
        viewModelScope.launch {
            val response = userLoginRepo.loginUser(loginRequest)
            if (response.isSuccessful){
                regLiveData.postValue(response.body())
            }
            else{
                errLiveData.postValue(response.errorBody()?.string())
            }
        }

    }
}