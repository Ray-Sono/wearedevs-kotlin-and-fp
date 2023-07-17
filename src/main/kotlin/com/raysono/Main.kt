package com.raysono

import com.raysono.Either.Left
import com.raysono.Either.Right
import com.raysono.Form.Draft
import com.raysono.ValidationError.Empty
import com.raysono.ValidationError.TooShort

fun main() {
    val invalidDraft = Draft(
        id = randomFormId(),
        name = "   ",
        description = "Never gonna give u up, ...",
    )
    val invalidForm = Form.Validated.fromDraft(invalidDraft)
    printDebug(invalidForm)

    val validDraft = Draft(
        id = randomFormId(),
        name = "Hello, Developer!",
        description = "Come work at Ray Sono! It's fun :)",
    )
    val validForm = Form.Validated.fromDraft(validDraft)
    printDebug(validForm)
}

private fun printDebug(maybeForm: Either<List<ValidationError>, Form.Validated>) {
    when (maybeForm) {
        is Left -> println("Invalid form:\n${format(maybeForm.value)}\n")
        is Right -> println("Valid form:\n${format(maybeForm.value)}\n")
    }
}

private fun format(errors: List<ValidationError>) = errors.map { error ->
    when (error) {
        is Empty -> "`${error.field}` is empty"
        is TooShort -> "`${error.field}` has ${error.actual} characters, but should have at least ${error.expected}"
    }
}.joinToString("\n") { "- $it" }

private fun format(form: Form.Validated) = buildString {
    appendLine("- ID: ${form.id.id}")
    appendLine("- Name: ${form.name}")
    appendLine("- Description: ${form.description}")
}
