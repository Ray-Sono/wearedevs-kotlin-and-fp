package com.raysono

import com.raysono.Form.Validated

sealed class Form {
    abstract val id: FormId

    /** A form that has not been validated yet. */
    data class Draft(
        override val id: FormId,
        val name: String?,
        val description: String?,
    ) : Form()

    /** A form that has been validated. */
    class Validated private constructor(
        override val id: FormId,
        val name: String,
        val description: String,
    ) : Form() {
        companion object {
            /** Validates the [draft] and either returns a [Validated] form or any validation errors. */
            fun fromDraft(draft: Draft): Either<List<ValidationError>, Validated> = validateDraft(draft) {
                Validated(draft.id, draft.name!!, draft.description!!)
            }
        }
    }
}

internal fun validateDraft(draft: Form.Draft, ifValid: () -> Validated) = buildList {
    add(validateNotBlank("name", draft.name))
    add(validateNotBlank("description", draft.description))
    add(validateMinLength("description", draft.description, minLength = 30))
}.concat(ifValid)
