package domain.value.generator

import domain.in.field.InputFieldScala
import domain.transform.calculations.CalculationsScala
import domain.transform.calculations.distribution.DistributionCalculationsScala
import domain.value.ValueScala

abstract class ValueGeneratorScala[T, V <: CalculationsScala[T]](inputField: InputFieldScala, calculations: V) {
  def obtainNext: ValueScala[T] = new ValueScala[T](
    this.inputField.info.id,
    this.inputField.info.`type`,
    this.inputField.info.name,
    this.calculations.calculate
  )

  def getId: String = this.inputField.info.id

  def increaseCounter(): Unit = {

    this.calculations match {
      case value: DistributionCalculationsScala[T] =>
        value.increaseDistributionCounter()
      case _ =>
    }

  }
}



