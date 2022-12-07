package com.getsporttrade.assignment.extension

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextLong
import kotlin.reflect.KClass

/**
 * Private instance of DecimalFormat to be used by the extension methods below
 */
private val decimalFormatter: DecimalFormat by lazy { DecimalFormat() }

/**
 * BigDecimal extension used to format values into currency or quantity strings
 *
 * @param showAsCurrency Boolean value to dictate whether the output should be displayed as
 * currency or not. Default value `false`.
 * @param maxDecimal Int value to determine the number of fractional values to display in the
 * resulting string decimal. Default value `2`.
 * @return The formatted String (e.g. $25.00 for currency values or 15.1235 for quantity values).
 */
fun BigDecimal.toAmountString(showAsCurrency: Boolean = false, maxDecimal: Int = 2): String {
    val formatter = decimalFormatter
    if (showAsCurrency) {
        formatter.apply {
            currency = Currency.getInstance(Locale.US)
            negativePrefix = "-${currency?.symbol}"
        }
    }
    formatter.maximumFractionDigits = maxDecimal
    formatter.minimumFractionDigits = if (showAsCurrency) { maxDecimal } else { 0 }

    return formatter.format(this)
}

/**
 * BigDecimal class/static extension for generating stub pricing values.
 *
 * @return A random BigDecimal value between 1.00 and 99.90 with a limit of 2 decimal places.
 */
fun <T> KClass<BigDecimal>.stubPrice(): BigDecimal {
    return BigDecimal.valueOf(nextDouble(1.0, 99.9)).setScale(2, RoundingMode.CEILING)
}

/**
 * BigDecimal class/static extension for generating stub quantity values.
 *
 * @param stubFractional Boolean value to dictate whether the response is in whole numbers or
 * fractional. Default value `false` meaning whole numbers will be returned.
 * @return A random BigDecimal value  between 1 and 10.
 */
fun <T> KClass<BigDecimal>.stubQuantity(stubFractional: Boolean = false): BigDecimal {
    return if (stubFractional) {
        BigDecimal.valueOf(nextDouble(1.0, 10.0))
    } else {
        BigDecimal.valueOf(nextLong(1, 10))
    }.setScale(4, RoundingMode.CEILING)
}
