package bills.project.billzapp.Repository

import bills.project.billzapp.API.ApiClient
import bills.project.billzapp.API.ApiInterface
import bills.project.billzapp.model.LoginRequest
import bills.project.billzapp.model.LoginResponse

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

