package bills.project.BillzApp.Repository

import bills.project.BillzApp.API.ApiClient
import bills.project.BillzApp.API.ApiInterface
import bills.project.BillzApp.model.LoginRequest
import bills.project.BillzApp.model.LoginResponse

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

