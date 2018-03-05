package domain.out.template

import java.io.{File, IOException, StringWriter}
import java.util

import domain.value.ValueScala
import freemarker.template.{Configuration, TemplateException, TemplateExceptionHandler}

import scala.collection.JavaConverters._

final class FreemakerTemplateSystemScala(nameTemplate: String, ouputType: String) extends TemplateSystemScala {
  private val ConfigurationVersion = Configuration.VERSION_2_3_26
  private val TemplatesFolder = "templates"
  private val DefaultEncoding = "UTF-8"
  private val TemplateVal = "outputs"
  private val configuration = new Configuration(this.ConfigurationVersion)

  override def configureTemplateSystem(): Unit = {
    this.configuration.setDirectoryForTemplateLoading(new File(this.TemplatesFolder))
    this.configuration.setDefaultEncoding(this.DefaultEncoding)
    this.configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    this.configuration.setLogTemplateExceptions(false)

  }

  override def obtainTemplateString(data: Seq[ValueScala[_]]): String = {
    var result = ""
    try {
      val root = new util.HashMap[String, util.List[ValueScala[_]]]
      root.put(this.TemplateVal, data.asJava)
      val template = this.configuration.getTemplate(this.nameTemplate)
      // For output in console, use this one. Testing only.
      // Writer out = new OutputStreamWriter(System.out);
      // For output in file, use this one.
      val out = new StringWriter
      template.process(root, out)
      result = out.getBuffer.toString
      out.flush()
      out.close()
    } catch {
      case e@(_: IOException | _: TemplateException) =>
        e.printStackTrace()
    }
    result
  }
}
