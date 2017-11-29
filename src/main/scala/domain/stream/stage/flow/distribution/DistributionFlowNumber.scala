package domain.stream.stage.flow.distribution

import java.lang

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the DistributionFlowT, used for Flows that receive Float values.
  *
  * @param dataGenerator     The data generator of this flow.
  * @param inputDistribution The info necessary to use distribution in this node.
  */
class DistributionFlowNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[java.lang.Float, Calculations[java.lang.Float]](dataGenerator, inputDistribution)
