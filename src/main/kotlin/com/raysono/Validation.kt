package com.raysono

import com.raysono.Either.Left
import com.raysono.Either.Right
import com.raysono.ValidationError.TooShort

/** Returns an error if [text] is null or blank. */
internal fun validateNotBlank(fieldName: String, text: String?) =
    if (text.isNullOrBlank()) Left(ValidationError.Empty(fieldName)) else Right(Unit)

/** Returns an error if [text] has fewer than [minLength] characters. */
internal fun validateMinLength(fieldName: String, text: String?, minLength: Int): Either<ValidationError, Unit> {
    val actualLength = text?.length ?: return Left(ValidationError.Empty(fieldName))
    return when {
        actualLength < minLength -> Left(TooShort(fieldName, minLength, actualLength))
        else -> Right(Unit)
    }
}

/**
 * Either returns a list of errors or, in case no errors exist, the result of invoking [transform].
 * This function is useful to perform validation on multiple fields and return a validated result if possible.
 */
internal fun <L, R, T> List<Either<L, R>>.concat(transform: () -> T): Either<List<L>, T> {
    val leftValues = fold(emptyList<L>()) { result, current -> current.fold({ result + it }, { result }) }
    return if (leftValues.isEmpty()) Right(transform()) else Left(leftValues)
}
