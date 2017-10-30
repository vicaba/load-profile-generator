package domain.stream.stage.flow.distribution

import java.lang

import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import infrastructure.value.preparation.ValueGenerator

class DistributionFlowNumber(override val dataGenerator: ValueGenerator[lang.Float, Calculations[lang.Float]],
                             override val inputDistribution: InputDistribution)
  extends DistributionFlowT[java.lang.Float, Calculations[java.lang.Float]](dataGenerator, inputDistribution)
