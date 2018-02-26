package domain.distribution.value

import java.time.LocalDateTime

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class DistributionFlowDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]],
                           override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator, inputDistribution)