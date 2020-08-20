package org.tb.gg.gameObject.components.physics

import org.tb.gg.gameObject.traits.TimePerishable

interface PerishCheckVisitor {
    boolean visit(TimePerishable perishable)
}