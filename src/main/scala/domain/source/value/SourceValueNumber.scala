package domain.source.value

import java.lang

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class SourceValueNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]])
  extends SourceValueT[java.lang.Float, Calculations[lang.Float]](dataGenerator)
