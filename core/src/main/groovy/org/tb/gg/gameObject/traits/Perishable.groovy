package org.tb.gg.gameObject.traits

import org.tb.gg.gameObject.PerishCondition

@PerishCondition
interface Perishable {
    Boolean shouldPerish()
}