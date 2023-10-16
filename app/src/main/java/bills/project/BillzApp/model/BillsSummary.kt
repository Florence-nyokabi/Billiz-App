package bills.project.BillzApp.model

data class BillsSummary(
    val paid: Double,
    val upcoming: Double,
    val overdue: Double,
    val total: Double
)
