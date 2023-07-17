package com.raysono

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class ValidationTest : StringSpec({

    "when validating that text is not blank, given null, an error should be returned" {
        validateNotBlank("fieldName", null).shouldBeInstanceOf<Either.Left<ValidationError>>()
    }

    "when validating that text is not blank, given blank text, an error should be returned" {
        forAll(blankTextArb) { text ->
            validateNotBlank("fieldName", text) is Either.Left
        }
    }

    "when validating that text is not blank, given non-blank text, no error should be returned" {
        forAll(nonBlankTextArb) { text ->
            validateNotBlank("fieldName", text) is Either.Right
        }
    }

    "when validating min length, given null, an error should be returned" {
        validateMinLength("fieldName", null, minLength = 0).shouldBeInstanceOf<Either.Left<ValidationError>>()
    }

    "when validating min length, given text that is too short, an error should be returned" {
        val minLength = 5
        forAll(Arb.string(minSize = 0, maxSize = minLength - 1)) { text ->
            validateMinLength("fieldName", text, minLength) is Either.Left
        }
    }

    "when validating min length, given text that is long enough, no error should be returned" {
        val minLength = 5
        forAll(Arb.string(minSize = minLength)) { text ->
            validateMinLength("fieldName", text, minLength) is Either.Right
        }
    }
})
