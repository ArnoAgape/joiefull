package com.openclassrooms.joiefull.utils

import com.openclassrooms.joiefull.ui.Utils
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for [Utils].
 *
 * These tests validate formatting of prices for accessibility.
 */
class UtilsTest {

    /**
     * Ensures [Utils.formatPriceForAccessibility] returns only euros
     * when the amount has no cents.
     */
    @Test
    fun `returns euros only when no cents`() {
        val result = Utils.formatPriceForAccessibility(20.0)
        assertEquals("20 euros", result)
    }

    /**
     * Ensures [Utils.formatPriceForAccessibility] returns both euros
     * and cents when the amount includes cents.
     */
    @Test
    fun `returns euros and cents when cents exist`() {
        val result = Utils.formatPriceForAccessibility(19.45)
        assertEquals("19 euros 45", result)
    }
}