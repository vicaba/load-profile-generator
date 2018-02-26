package stream.distribution.infrastructure.value

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator
import stream.distribution.domain.DistributionFlowT

final class DistributionFlowString(override val dataGenerator: ValueGenerator[String, Calculations[String]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[String, Calculations[String]](dataGenerator, inputDistribution)
