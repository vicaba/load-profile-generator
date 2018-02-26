package stream.distribution.infrastructure

import java.time.LocalDateTime

import stream.distribution.domain.DistributionFlowT
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class DistributionFlowDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]],
                           override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator, inputDistribution)