package bills.project.billmanagementapp.Repository

import bills.project.billmanagementapp.API.ApiClient
import bills.project.billmanagementapp.API.ApiInterface
import bills.project.billmanagementapp.model.RegisterRequest
import bills.project.billmanagementapp.model.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)

    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>{
        return withContext(Dispatchers.IO){
            apiClient.registerUser(registerRequest)
        }
    }
}