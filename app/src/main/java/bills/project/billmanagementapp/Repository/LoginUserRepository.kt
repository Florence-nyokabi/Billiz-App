package bills.project.billmanagementapp.Repository

import bills.project.billmanagementapp.API.ApiClient
import bills.project.billmanagementapp.API.ApiInterface
import bills.project.billmanagementapp.model.LoginRequest
import bills.project.billmanagementapp.model.LoginResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginUserRepository {

    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return withContext(Dispatchers.IO){
            apiClient.loginUser(loginRequest)
        }
    }
}

