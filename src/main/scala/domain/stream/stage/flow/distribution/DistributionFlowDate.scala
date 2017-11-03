package domain.stream.stage.flow.distribution

import java.time.LocalDateTime

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import infrastructure.value.preparation.ValueGenerator

class DistributionFlowDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]],
                           override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator, inputDistribution)