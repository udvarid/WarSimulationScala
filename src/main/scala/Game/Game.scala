package Game

import Soldier._
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

case class Cell(x: Int, y: Int)

class Game(armySize: Int, canvas: Canvas) {

  val soldiers: List[Soldier] = SoldierFactory.createArmy(armySize)
  val soldierPainter: Visitor = new DrawerVisitor(canvas)

  def draw(soldier: List[Soldier]): Unit = {
    drawBasicLines
    soldiers.foreach(s => s.accept(soldierPainter))
  }

  private def drawBasicLines: Unit = {
    val size = armySize * 25
    val gc = canvas.graphicsContext2D
    gc.clearRect(0, 0, canvas.width.value, canvas.height.value)

    gc.fill = Color.Green
    gc.stroke = Color.Green
    gc.lineWidth = 2
    gc.strokeLine(0, 0, 0, size - 1)
    gc.strokeLine(0, size - 1, size - 1, size - 1)
    gc.strokeLine(size - 1, size - 1, size - 1, 0)
    gc.strokeLine(size - 1, 0, 0, 0)
    gc.lineWidth = 1
    for (x <- 1 to size / 25) {
      gc.strokeLine(x * 25, 0, x * 25, size)
      gc.strokeLine(0, x * 25, size, x * 25)
    }
  }




}
