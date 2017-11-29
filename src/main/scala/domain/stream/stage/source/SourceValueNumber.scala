package domain.stream.stage.source

import java.lang

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the SourceValueT, used for Sources that generate Float values.
  *
  * @param dataGenerator The data generator of this flow.
  */
class SourceValueNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]])
  extends SourceValueT[java.lang.Float, Calculations[lang.Float]](dataGenerator)
