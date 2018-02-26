package distribution.stream.infrastructure

import distribution.stream.domain.DistributionFlowT
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the DistributionFlowT, used for Flows that receive String values.
  *
  * @param dataGenerator     The data generator of this flow.
  * @param inputDistribution The info necessary to use distribution in this node.
  */
class DistributionFlowString(override val dataGenerator: ValueGenerator[String, Calculations[String]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[String, Calculations[String]](dataGenerator, inputDistribution)
