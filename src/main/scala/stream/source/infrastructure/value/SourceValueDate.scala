package stream.source.infrastructure.value

import java.time.LocalDateTime

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator
import stream.source.domain.SourceValueT

final class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]])
  extends SourceValueT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator)
