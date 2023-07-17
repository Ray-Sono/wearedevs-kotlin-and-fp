package com.raysono

/** Errors that can occur during validation. */
sealed class ValidationError {
    /** The [field] has an empty value. */
    class Empty(val field: String) : ValidationError()

    /** The value of the [field] has less than the [expected] number of characters. */
    class TooShort(val field: String, val expected: Int, val actual: Int) : ValidationError()
}
