package domain.out.template

import domain.value.ValueScala

final class TemplateOutputScala(templateSystem: TemplateSystemScala) {
  def configureTemplateSystem(): Unit = {
    this.templateSystem.configureTemplateSystem()
  }

  def obtainTemplateString(data: Seq[ValueScala[_]]): String = {
    this.templateSystem.obtainTemplateString(data)
  }
}
