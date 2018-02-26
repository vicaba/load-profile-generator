package domain.distribution.value

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

final class DistributionFlowString(override val dataGenerator: ValueGenerator[String, Calculations[String]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[String, Calculations[String]](dataGenerator, inputDistribution)
