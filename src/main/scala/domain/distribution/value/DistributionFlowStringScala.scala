package domain.distribution.value

import domain.in.distribution.InputDistributionScala
import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class DistributionFlowStringScala(override val dataGenerator: ValueGeneratorScala[String, CalculationsScala[String]],
                                        override val inputDistribution: Seq[InputDistributionScala])
  extends DistributionFlowTScala[String, CalculationsScala[String]](dataGenerator, inputDistribution)
