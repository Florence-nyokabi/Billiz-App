package bills.project.billzapp.utils
import java.lang.IllegalArgumentException
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalAdjusters.nextOrSame
import java.time.temporal.TemporalAdjusters.previousOrSame

class DateTimeUtils {
    companion object{
        fun formatDate(LocalDate:LocalDateTime):String {
            val sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return sdf.format(LocalDate)
        }
        fun getFirstDayOfWeek():String{
            val now = LocalDateTime.now()
            val first=  now.with(previousOrSame(DayOfWeek.MONDAY))
            return  formatDate(first)
        }
        fun getFirstDayOfMonth(): String {
            val now = LocalDateTime.now()
            val firstDay = now.with(TemporalAdjusters.firstDayOfMonth())
            return formatDate(firstDay)
        }
        fun getLastDayOfMonth(): String {
            val now = LocalDateTime.now()
            val lastDay = now.with(TemporalAdjusters.lastDayOfMonth())
            return formatDate(lastDay)
        }
        fun getCurrentMonth(): String {
            val now = LocalDateTime.now()
            return now.month.value.toString()
        }
        fun getLastDayOfWeek(): String {
            val now = LocalDateTime.now()
            val first=  now.with(nextOrSame(DayOfWeek.SUNDAY))
            return  formatDate(first)
        }
        fun createDateFromDayAndMonth(day: Int, month: Int): String {
            val now = LocalDateTime.now()
            val date = now.withMonth(month).withDayOfMonth(day)
            return formatDate(date)
        }
        fun getDateOfWeekDay(day:String): String {
            val now = LocalDateTime.now()
            val date= now.with(nextOrSame(DayOfWeek.of(day.toInt())))
            return formatDate(date)
        }
        fun createDateFromDay(dayOfMonth: String): String {
            val now = LocalDateTime.now()
            val date = now.withDayOfMonth(dayOfMonth.toInt())
            return formatDate(date)
        }

        fun formatDateReadable(date: String): String {
            val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateToFormat = LocalDate.parse(date, originalFormat)
            val readableFormat = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
            return readableFormat.format(dateToFormat)
        }
        fun getQuarterStartDate(year: String, quarter: Int): String{
            val yearInt = year.toInt()
            val quarterStartMonth: Int = when(quarter){
                1 -> 1
                2 -> 4
                3 -> 7
                4 -> 10
                else -> throw  IllegalArgumentException("Invalid quarter: $quarter")
            }
            val firstDay = LocalDate.of(yearInt, quarterStartMonth, 1)
            return formatDate(firstDay.atStartOfDay())
        }

        fun getQuarterEndDate(year: String, quarter: Int): String {
            val yearInt = year.toInt()
            val quarterEndMonth: Int = when (quarter) {
                1 -> 3
                2 -> 6
                3 -> 9
                4 -> 12
                else -> throw IllegalArgumentException("Invalid quarter: $quarter")
            }

            val lastDay = LocalDate.of(yearInt, quarterEndMonth, 1).withDayOfMonth(1).plusMonths(1).minusDays(1)
            return formatDate(lastDay.atStartOfDay())
        }

        fun getCurrentYear():String{
            val now= LocalDateTime.now()
            return now.year.toString()
        }

        fun formatCurrency(amount: Double):String{
            val formatter = DecimalFormat("KES #,###.##")
            return  formatter.format(amount)
        }

        fun getDateToday():String{
            return formatDate(LocalDateTime.now())
        }
    }
}
