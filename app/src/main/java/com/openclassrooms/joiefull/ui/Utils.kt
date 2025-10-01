package com.openclassrooms.joiefull.ui

import android.icu.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Utility functions for formatting prices and amounts.
 */
object Utils {

    /**
     * Formats a price for accessibility (e.g., "49 euros 99").
     *
     * @param price The price as Double.
     * @return A formatted string.
     */
    fun formatPriceForAccessibility(price: Double): String {
        val euros = price.toInt()
        val cents = ((price - euros) * 100).roundToInt()

        return if (cents == 0) {
            "$euros euros"
        } else {
            "$euros euros $cents"
        }
    }

    /**
     * Formats an amount using the given locale’s currency.
     *
     * @param amount The price value.
     * @param locale The locale to use for formatting.
     * @return The formatted amount string.
     */
    fun formatAmount(amount: Double, locale: Locale): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }
}