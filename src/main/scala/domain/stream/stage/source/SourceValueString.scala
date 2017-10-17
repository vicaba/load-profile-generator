package domain.stream.stage.source

import domain.transform.calculations.StringEqualCalculations
import infrastructure.value.preparation.ValueGenerator

class SourceValueString(override val dataGenerator: ValueGenerator[String, StringEqualCalculations])
    extends SourceValueT[String, StringEqualCalculations](dataGenerator)
