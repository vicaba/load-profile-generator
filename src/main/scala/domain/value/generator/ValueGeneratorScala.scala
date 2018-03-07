package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala
import domain.transform.calculations.distribution.DistributionCalculationsScala
import domain.value.ValueScala

abstract class ValueGeneratorScala[T, V <: CalculationsScala[T]](inputField: InputFieldScala, calculations: V) {
  def obtainNext: ValueScala[T] = new ValueScala[T](
    this.inputField.getId,
    this.inputField.getType,
    this.inputField.getName,
    this.calculations.calculate
  )

  def getId: String = inputField.getId

  def getName: String = inputField.getName

  def increaseCounter(): Unit = {

    this.calculations match {
      case value: DistributionCalculationsScala[T] =>
        value.increaseDistributionCounter()
      case _ =>
    }

  }
}



