package com.getsporttrade.assignment.service.cache.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PositionTest {
    private val position = Position(
        identifier = "ny-jets",
        name = "NY Jets",
        criteriaName = "Win or lose",
        storyName = "NY Jets @ PHI Eagles",
        price = 21.34.toBigDecimal(),
        quantity = 10.00.toBigDecimal()
    )

    @Test
    fun `verify position price currency format`() {
        val startsWithDollarSign = position.formattedPrice.startsWith("$")
        assertTrue(startsWithDollarSign)
        assertEquals("$21.34", position.formattedPrice)
    }

    @Test
    fun `verify quantity with 4 significant digits`() {
        val (whole, decimal) = position.formattedQty.split(".")

        val wholeLen = whole.length
        assertTrue(wholeLen >= 1)

        val decimalLen = decimal.length
        assertEquals(4, decimalLen)

        assertEquals("10.0000", position.formattedQty)
    }
}