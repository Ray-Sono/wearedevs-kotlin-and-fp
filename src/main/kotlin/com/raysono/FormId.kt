package com.raysono

import java.util.UUID

/** Unique identifier of a [Form]. */
@JvmInline
value class FormId(val id: String)

/** Returns a random [FormId]. */
fun randomFormId(): FormId = FormId(UUID.randomUUID().toString())
