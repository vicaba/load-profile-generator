import infrastructure.in.config.json.deserializer.InputConfigurationJsonReaderScala

object MainTest {
  def main(args: Array[String]): Unit = {
    val inputConfigurationFile = "resources/input.json"
    print(new InputConfigurationJsonReaderScala(inputConfigurationFile).read())
  }
}
