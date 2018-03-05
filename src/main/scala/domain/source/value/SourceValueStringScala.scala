package domain.source.value

import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class SourceValueStringScala(override val dataGenerator: ValueGeneratorScala[String, CalculationsScala[String]])
  extends SourceValueTScala[String, CalculationsScala[String]](dataGenerator)
