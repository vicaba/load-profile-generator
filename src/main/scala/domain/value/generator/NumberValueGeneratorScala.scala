package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala

final class NumberValueGeneratorScala(val inputField: InputFieldScala, val calculations: CalculationsScala[Double])
  extends ValueGeneratorScala[Double, CalculationsScala[Double]](inputField, calculations)

