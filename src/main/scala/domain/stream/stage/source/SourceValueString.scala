package domain.stream.stage.source

import domain.transform.calculations.Calculations
import infrastructure.value.preparation.ValueGenerator

class SourceValueString(override val dataGenerator: ValueGenerator[String, Calculations[String]])
  extends SourceValueT[String, Calculations[String]](dataGenerator)
