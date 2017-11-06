package domain.stream.stage.source

import java.time.LocalDateTime

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]])
  extends SourceValueT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator)
