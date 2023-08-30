package bills.project.BillzApp.Repository

import bills.project.BillzApp.API.ApiClient
import bills.project.BillzApp.API.ApiInterface
import bills.project.BillzApp.model.RegisterRequest
import bills.project.BillzApp.model.RegisterResponse
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