package domain.out.template

import domain.value.ValueScala

trait TemplateSystemScala {
  def configureTemplateSystem(): Unit

  def obtainTemplateString(data: Seq[ValueScala[_]]): String
}
