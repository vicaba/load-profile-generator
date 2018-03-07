package domain.distribution.value

import domain.in.distribution.InputDistributionScala
import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class DistributionFlowNumberScala(override val dataGenerator: ValueGeneratorScala[Double, CalculationsScala[Double]],
                                        override val inputDistribution: Seq[InputDistributionScala])
  extends DistributionFlowTScala[Double, CalculationsScala[Double]](dataGenerator, inputDistribution)
