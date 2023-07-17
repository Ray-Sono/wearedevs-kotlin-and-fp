package com.raysono

import com.raysono.Either.Left
import com.raysono.Either.Right

/**
 * Represents either an error or a successful outcome.
 * [Left] instances represent an error state whereas [Right] instances represent a successful result.
 */
sealed class Either<out Failure, out Success> {
    class Left<Failure>(val value: Failure) : Either<Failure, Nothing>()

    class Right<Success>(val value: Success) : Either<Nothing, Success>()
}

/**
 * Transforms this instance to a new one by applying the [transform] function to the value of [Right] instances.
 * [Left] instances will be returned as is.
 */
inline fun <L, R, T> Either<L, R>.flatMap(transform: (R) -> Either<L, T>): Either<L, T> = when (this) {
    is Left -> this
    is Right -> transform(value)
}

/**
 * Folds the value of both cases into a single value of type [T] by applying the [left] and
 * [right] functions accordingly.
 */
inline fun <L, R, T> Either<L, R>.fold(left: (L) -> T, right: (R) -> T): T = when (this) {
    is Left -> left(value)
    is Right -> right(value)
}

/** Returns the value [R] in case this is a [Right] instance. Otherwise, null. */
fun <L, R> Either<L, R>.rightOrNull(): R? = (this as? Right)?.value

/** Returns the value [L] in case this is a [Left] instance. Otherwise, null. */
fun <L, R> Either<L, R>.leftOrNull(): L? = (this as? Left)?.value
