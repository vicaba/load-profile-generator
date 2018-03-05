package domain.source.value

import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class SourceValueNumberScala(override val dataGenerator: ValueGeneratorScala[Float, CalculationsScala[Float]])
  extends SourceValueTScala[Float, CalculationsScala[Float]](dataGenerator)
