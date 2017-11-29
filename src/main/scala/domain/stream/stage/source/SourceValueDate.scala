package domain.stream.stage.source

import java.time.LocalDateTime

import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the SourceValueT, used for Sources that generate LocalDateTime values.
  *
  * @param dataGenerator The data generator of this flow.
  */
class SourceValueDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]])
  extends SourceValueT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator)
