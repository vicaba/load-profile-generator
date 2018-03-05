package domain.distribution.value

import java.time.LocalDateTime

import domain.in.distribution.InputDistributionScala
import domain.transform.calculations.CalculationsScala
import domain.value.generator.ValueGeneratorScala

final class DistributionFlowDateScala(override val dataGenerator: ValueGeneratorScala[LocalDateTime, CalculationsScala[LocalDateTime]],
                                      override val inputDistribution: Seq[InputDistributionScala])
  extends DistributionFlowTScala[LocalDateTime, CalculationsScala[LocalDateTime]](dataGenerator, inputDistribution)