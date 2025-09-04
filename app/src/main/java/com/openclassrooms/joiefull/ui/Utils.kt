package com.openclassrooms.joiefull.ui

import android.icu.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

object Utils {

    fun formatPriceForAccessibility(price: Double): String {
        val euros = price.toInt()
        val cents = ((price - euros) * 100).roundToInt()

        return if (cents == 0) {
            "$euros euros"
        } else {
            "$euros euros $cents"
        }
    }
    fun formatAmount(amount: Double, locale: Locale): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }
}