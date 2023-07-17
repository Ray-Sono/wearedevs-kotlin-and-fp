package com.raysono

import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.string

/** Arbitrary that returns text consisting only of whitespace. */
internal val blankTextArb = Arb.string().filter { it.isBlank() }

/** Arbitrary that returns text that is not blank. */
internal val nonBlankTextArb = Arb.string(minSize = 1).filter { it.isNotBlank() }
