package domain.in.config

import domain.in.field.InputFieldScala


final class InputConfigurationScala(fields: List[InputFieldScala]) {
  def getFields: List[InputFieldScala] = this.fields

  def getField(fieldIndex: Int): InputFieldScala = this.fields(fieldIndex)

}
