package domain.out.template

import java.io.File

import freemarker.template.{Configuration, TemplateExceptionHandler, Version}

final class FreemakerTemplateSystemScala(nameTemplate:String, ouputType:String) extends TemplateSystemScala {
  private val ConfigurationVersion = Configuration.VERSION_2_3_26
  private val TemplatesFolder = "templates"
  private val DefaultEncoding = "UTF-8"
  private val configuration = new Configuration(this.ConfigurationVersion)

  override def configureTemplateSystem(): Unit = {
    this.configuration.setDirectoryForTemplateLoading(new File(this.TemplatesFolder))
    this.configuration.setDefaultEncoding(this.DefaultEncoding)
    this.configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    this.configuration.setLogTemplateExceptions(false)

  }
}
