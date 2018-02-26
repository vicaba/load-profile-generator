package stream.distribution.infrastructure.value

import java.lang

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.generator.ValueGenerator
import stream.distribution.domain.DistributionFlowT

final class DistributionFlowNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]],
                             override val inputDistribution: List[InputDistribution])
  extends DistributionFlowT[java.lang.Float, Calculations[java.lang.Float]](dataGenerator, inputDistribution)
