package domain.stream.stage.source

import java.time.LocalDateTime

import domain.transform.calculations.equal.DateEqualCalculations
import infrastructure.value.preparation.ValueGenerator

class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, DateEqualCalculations])
  extends SourceValueT[LocalDateTime, DateEqualCalculations](dataGenerator)
