package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala

final class StringValueGeneratorScala(val inputField: InputFieldScala[String], val calculations: CalculationsScala[String])
  extends ValueGeneratorScala[String, CalculationsScala[String]](inputField, calculations)