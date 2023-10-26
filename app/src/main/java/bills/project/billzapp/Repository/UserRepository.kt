package bills.project.billzapp.Repository

import bills.project.billzapp.API.ApiClient
import bills.project.billzapp.API.ApiInterface
import bills.project.billzapp.model.RegisterRequest
import bills.project.billzapp.model.RegisterResponse
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