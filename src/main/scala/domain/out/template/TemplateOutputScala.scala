package domain.out.template

final class TemplateOutputScala(templateSystem: TemplateSystemScala) {
  def configureTemplateSystem(): Unit = {
    this.templateSystem.configureTemplateSystem()
  }


}
