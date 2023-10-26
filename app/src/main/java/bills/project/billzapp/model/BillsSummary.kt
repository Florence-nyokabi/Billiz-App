package bills.project.billzapp.model

data class BillsSummary(
    val paid: Double,
    val upcoming: Double,
    val overdue: Double,
    val total: Double
)
