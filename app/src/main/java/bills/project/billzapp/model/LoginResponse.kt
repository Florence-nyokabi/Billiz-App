package bills.project.billzapp.model

data class LoginResponse(
    var message: String,
    var accessToken: String,
    var userId: String
)
