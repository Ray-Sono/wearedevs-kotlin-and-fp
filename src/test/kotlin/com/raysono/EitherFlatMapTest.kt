package com.raysono

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EitherFlatMapTest : StringSpec({

    "when flat-mapping, given a left instance, it should be returned as is" {
        // given
        val expected = Either.Left("error")

        // when
        val actual = expected.flatMap { Either.Right("new value") }

        // then
        actual shouldBe expected
    }

    "when flat-mapping, given a left instance, the transform function should not be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Left("error")

        // when
        original.flatMap { didInvokeFunction = true; original }

        // then
        didInvokeFunction shouldBe false
    }

    "when flat-mapping, given a right instance, the transformed value should be returned" {
        // given
        val original = Either.Right("original")
        val expected = Either.Right("new value")

        // when
        val actual = original.flatMap { expected }

        // then
        actual shouldBe expected
    }

    "when flat-mapping, given a right instance, the transform function should be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Right("original")

        // when
        original.flatMap { didInvokeFunction = true; original }

        // then
        didInvokeFunction shouldBe true
    }
})
