package com.raysono

import com.raysono.Form.Draft
import com.raysono.Form.Validated
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll

class FormValidationTest : StringSpec({

    "when validating draft, given blank name, an error should be returned" {
        forAll(
            arbitrary {
                validDraftArb.bind().copy(name = blankTextArb.bind())
            },
        ) { draft ->
            Validated.fromDraft(draft).leftOrNull()!!.any {
                it is ValidationError.Empty && it.field == "name"
            }
        }
    }

    "when validating draft, given blank description, an error should be returned" {
        forAll(
            arbitrary {
                validDraftArb.bind().copy(description = blankTextArb.bind())
            },
        ) { draft ->
            Validated.fromDraft(draft).leftOrNull()!!.any {
                it is ValidationError.Empty && it.field == "description"
            }
        }
    }

    "when validating draft, given description is too short, an error should be returned" {
        forAll(
            arbitrary {
                validDraftArb.bind().copy(description = nonBlankTextArb.filter { it.length < 30 }.bind())
            },
        ) { draft ->
            Validated.fromDraft(draft).leftOrNull()!!.any {
                it is ValidationError.TooShort && it.field == "description"
            }
        }
    }

    "when validating a draft, given all properties are valid, a valid form should be returned" {
        forAll(validDraftArb) { draft ->
            val form = Validated.fromDraft(draft).rightOrNull()!!
            form.id == draft.id && form.name == draft.name && form.description == draft.description
        }
    }
})

private val formIdArb = Arb.string(minSize = 1).map(::FormId)
private val validDraftArb = arbitrary {
    Draft(
        id = formIdArb.bind(),
        name = nonBlankTextArb.bind(),
        description = nonBlankTextArb.filter { it.length >= 30 }.bind(),
    )
}
