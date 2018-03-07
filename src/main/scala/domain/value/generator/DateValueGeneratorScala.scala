package domain.value.generator

import java.time.LocalDateTime

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala

final class DateValueGeneratorScala(val inputField: InputFieldScala, val calculations: CalculationsScala[LocalDateTime])
  extends ValueGeneratorScala[LocalDateTime, CalculationsScala[LocalDateTime]](inputField, calculations)

