package com.openclassrooms.joiefull.repository.utils

import com.openclassrooms.joiefull.ui.Utils
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun `returns euros only when no cents`() {
        val result = Utils.formatPriceForAccessibility(20.0)
        assertEquals("20 euros", result)
    }

    @Test
    fun `returns euros and cents when cents exist`() {
        val result = Utils.formatPriceForAccessibility(19.45)
        assertEquals("19 euros 45", result)
    }
}