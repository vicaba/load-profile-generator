package domain.stream.stage.source

import java.lang

import domain.transform.calculations.equal.NumberEqualCalculations
import infrastructure.value.preparation.ValueGenerator

class SourceValueNumber(override val dataGenerator: ValueGenerator[lang.Float, NumberEqualCalculations])
  extends SourceValueT[java.lang.Float, NumberEqualCalculations](dataGenerator)
