package domain.stream.stage.flow.distribution

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import infrastructure.value.preparation.ValueGenerator

class DistributionFlowString(override val dataGenerator: ValueGenerator[String, Calculations[String]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[String, Calculations[String]](dataGenerator, inputDistribution)
