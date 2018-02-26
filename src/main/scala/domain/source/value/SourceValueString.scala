package domain.source.value

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class SourceValueString(override val dataGenerator: ValueGenerator[String, Calculations[String]])
  extends SourceValueT[String, Calculations[String]](dataGenerator)
