package domain.stream.stage.flow.distribution

import java.time.LocalDateTime

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator

/**
  * Class that extends the DistributionFlowT, used for Flows that receive LocalDateTime values.
  *
  * @param dataGenerator     The data generator of this flow.
  * @param inputDistribution The info necessary to use distribution in this node.
  */
class DistributionFlowDate(override val dataGenerator: ValueGenerator[LocalDateTime, Calculations[LocalDateTime]],
                           override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[LocalDateTime, Calculations[LocalDateTime]](dataGenerator, inputDistribution)