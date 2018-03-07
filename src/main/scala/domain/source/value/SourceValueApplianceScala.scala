package domain.source.value

import domain.transform.calculations.CalculationsScala
import domain.value.appliance.Appliance
import domain.value.generator.ValueGeneratorScala

final class SourceValueApplianceScala(override val dataGenerator: ValueGeneratorScala[Appliance, CalculationsScala[Appliance]])
  extends SourceValueTScala[Appliance, CalculationsScala[Appliance]](dataGenerator)


