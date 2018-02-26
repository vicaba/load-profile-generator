package stream.source.infrastructure

import java.time.LocalDateTime

import stream.source.domain.SourceValueT
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]])
  extends SourceValueT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator)
