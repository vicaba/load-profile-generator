package domain.in.field

final case class InputFieldInfoScala(private val id: String, private val name: String, private val `type`: String) {
  def getId: String = this.id

  def getName: String = this.name

  def getType: String = this.`type`

}
