package bills.project.billmanagementapp.model

data class LoginResponse(
    var message: String,
    var accessToken: String,
    var userId: String
)
