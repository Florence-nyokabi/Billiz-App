package bills.project.billmanagementapp.model

data class LoginResponse(
    var message: String,
    var access_token: String,
    var user_id: String
)
