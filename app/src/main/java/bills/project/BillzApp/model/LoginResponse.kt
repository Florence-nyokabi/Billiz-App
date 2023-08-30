package bills.project.BillzApp.model

data class LoginResponse(
    var message: String,
    var accessToken: String,
    var userId: String
)
