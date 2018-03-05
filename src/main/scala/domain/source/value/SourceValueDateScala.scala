package domain.source.value

import java.time.LocalDateTime

import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class SourceValueDateScala(override val dataGenerator: ValueGeneratorScala[LocalDateTime, CalculationsScala[LocalDateTime]])
  extends SourceValueTScala[LocalDateTime, CalculationsScala[LocalDateTime]](dataGenerator)
