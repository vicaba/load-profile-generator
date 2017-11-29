package domain.stream.stage.source

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the SourceValueT, used for Sources that generate String values.
  *
  * @param dataGenerator The data generator of this flow.
  */
class SourceValueString(override val dataGenerator: ValueGenerator[String, Calculations[String]])
  extends SourceValueT[String, Calculations[String]](dataGenerator)
