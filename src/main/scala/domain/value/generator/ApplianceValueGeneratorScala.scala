package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala
import domain.value.appliance.Appliance

final class ApplianceValueGeneratorScala(val inputField: InputFieldScala, val calculations: CalculationsScala[Appliance])
  extends ValueGeneratorScala[Appliance, CalculationsScala[Appliance]](inputField, calculations)


