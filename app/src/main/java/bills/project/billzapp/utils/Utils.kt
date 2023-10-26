package bills.project.billzapp.utils

import android.view.View
import java.text.DecimalFormat

class Utils {
    companion object{
        fun formatCurrency(amount: Double):String{
            val fmt = DecimalFormat("KES #,###")
            return fmt.format(amount)
        }

        fun View.show(){

        }

    }
}