package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala

final class NumberValueGeneratorScala(val inputField: InputFieldScala[Float], val calculations: CalculationsScala[Float])
  extends ValueGeneratorScala[Float, CalculationsScala[Float]](inputField, calculations)

