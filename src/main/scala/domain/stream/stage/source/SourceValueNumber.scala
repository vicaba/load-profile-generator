package domain.stream.stage.source

import java.lang

import domain.transform.calculations.Calculations
import infrastructure.value.preparation.ValueGenerator

class SourceValueNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]])
  extends SourceValueT[java.lang.Float, Calculations[lang.Float]](dataGenerator)
