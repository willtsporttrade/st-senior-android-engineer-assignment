package com.getsporttrade.assignment.utils

/**
 * Sealed class wrapper for sending optional values in rxObservable streams
 */
sealed class Optional<out T : Any> {
    abstract val isNull: Boolean
}

/**
 * Wrapper object for sending null values in rxObservable streams
 */
object None : Optional<Nothing>() {
    override val isNull = true
}

/**
 * Wrapper class for sending optional values in rxObservable streams
 */
data class Some<T : Any>(val value: T) : Optional<T>() {
    override val isNull: Boolean = false
}

/**
 * Property accessor for getting the wrapped item
 */
val <T : Any> Optional<T>.value: T?
    get() = when (this) { is Some -> value else -> null }

/**
 * Convenience method for wrapping an object with Optional
 */
fun <T : Any> T?.asOptional(): Optional<T> {
    return this?.let(::Some) ?: None
}

/**
 * Convenience method for getting a list from the optional where the internal value is iterable
 */
fun <T : Any> Optional<T>.asIterable(): Iterable<T> {
    return listOfNotNull(value)
}

/**
 * Convenience method for filtering optional values
 */
inline fun <T : Any> Optional<T>.filter(predicate: (T) -> Boolean): Optional<T> {
    return value?.takeIf(predicate)?.let { this } ?: None
}

/**
 * Convenience method for flatMapping optional values
 */
inline fun <T : Any, U : Any> Optional<T>.flatMap(mapper: (T) -> Optional<U>): Optional<U> {
    return value?.let(mapper) ?: None
}

/**
 * Convenience method for performing scope functions on optional values
 */
inline fun <T : Any> Optional<T>.ifPresent(consumer: (T) -> Unit) {
    value?.let(consumer)
}

/**
 * Convenience method for mapping optional values
 */
inline fun <T : Any, U : Any> Optional<T>.map(mapper: (T) -> U): Optional<U> {
    return flatMap { Some(mapper(it)) }
}

/**
 * Convenience method for taking the optional value or performing a custom callback
 */
inline fun <T : Any> Optional<T>.orElse(other: () -> T): T {
    return value ?: other()
}
