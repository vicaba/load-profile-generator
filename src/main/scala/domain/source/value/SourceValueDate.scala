package domain.source.value

import java.time.LocalDateTime

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]])
  extends SourceValueT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator)
