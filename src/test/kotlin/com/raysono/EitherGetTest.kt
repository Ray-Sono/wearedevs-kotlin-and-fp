package com.raysono

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EitherGetTest : StringSpec({

    "when getting right value, given a left instance, null should be thrown" {
        // given
        val expected: Either<String, String> = Either.Left("error")

        // when
        val actual = expected.rightOrNull()

        // then
        actual shouldBe null
    }

    "when getting right value, given a right instance, the value should be returned" {
        // given
        val expected = "value"

        // when
        val actual = Either.Right(expected).rightOrNull()

        // then
        actual shouldBe expected
    }

    "when getting left value, given a right instance, null should be returned" {
        // given
        val expected: Either<String, String> = Either.Right("success")

        // when
        val actual = expected.leftOrNull()

        // then
        actual shouldBe null
    }

    "when getting left, given a left instance, the value should be returned" {
        // given
        val expected = "value"

        // when
        val actual = Either.Left(expected).leftOrNull()

        // then
        actual shouldBe expected
    }
})
