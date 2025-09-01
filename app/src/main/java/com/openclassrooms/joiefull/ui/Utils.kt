package com.openclassrooms.joiefull.ui

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
}