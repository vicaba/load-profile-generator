package stream.source.infrastructure.value

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator
import stream.source.domain.SourceValueT


final class SourceValueString(override val dataGenerator: ValueGenerator[String, Calculations[String]])
  extends SourceValueT[String, Calculations[String]](dataGenerator)
