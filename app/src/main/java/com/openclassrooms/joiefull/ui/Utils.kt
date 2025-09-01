package com.openclassrooms.joiefull.ui

object Utils {

    fun formatPriceForAccessibility(price: Double): String {
        val euros = price.toInt()
        val cents = ((price - euros) * 100).toInt()

        return if (cents == 0) {
            "$euros euros"
        } else {
            "$euros euros $cents"
        }
    }

    fun formatRateForAccessibility(rate: Double): String {
        val newRate = rate.toInt()
        return "$newRate"
    }

}