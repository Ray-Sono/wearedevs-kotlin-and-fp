package com.raysono

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EitherFoldTest : StringSpec({

    "when folding, given a left instance, the transformed value should be returned" {
        // given
        val original = Either.Left("error")
        val expected = "new value"

        // when
        val actual = original.fold({ expected }, { "unexpected" })

        // then
        actual shouldBe expected
    }

    "when folding, given a left instance, the left transform function should be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Left("error")

        // when
        original.fold({ didInvokeFunction = true; "foo" }, { "foo" })

        // then
        didInvokeFunction shouldBe true
    }

    "when folding, given a left instance, the right transform function should not be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Left("error")

        // when
        original.fold({ "foo" }, { didInvokeFunction = true; "foo" })

        // then
        didInvokeFunction shouldBe false
    }

    "when folding, given a right instance, the transformed value should be returned" {
        // given
        val original = Either.Right("success")
        val expected = "new value"

        // when
        val actual = original.fold({ "unexpected" }, { expected })

        // then
        actual shouldBe expected
    }

    "when folding, given a right instance, the right transform function should be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Right("success")

        // when
        original.fold({ "foo" }, { didInvokeFunction = true; "foo" })

        // then
        didInvokeFunction shouldBe true
    }

    "when folding, given a right instance, the left transform function should not be executed" {
        // given
        var didInvokeFunction = false
        val original = Either.Right("success")

        // when
        original.fold({ didInvokeFunction = true; "foo" }, { "foo" })

        // then
        didInvokeFunction shouldBe false
    }
})
