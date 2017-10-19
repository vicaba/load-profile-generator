package domain.stream.stage.conversion

trait ConvertListXToListY[X, Y] {
  val in: List[X]

  def convert(): List[Y]
}
