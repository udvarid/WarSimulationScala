package Game

import Soldier._
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

import scala.util.Random

case class Cell(x: Int, y: Int)

class Game(armySize: Int, canvas: Canvas) {

  val soldiers: List[Soldier] = SoldierFactory.createArmy(armySize)
  val soldierPainter: Visitor = new DrawerVisitor(canvas)

  def draw(): Unit = {
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

  def startGame() = {
    val threads: List[Thread] = soldiers.map(s => new Thread(() => letsRock(s)))
    threads.foreach(t => t.start())
  }

  def letsRock(soldier: Soldier) = {
    var number = 0
    while (soldier.liveStatus && number < 25) {
      soldierAction(soldier)
      number += 1
      Thread.sleep(new Random().nextInt(1000))
    }
  }

  def soldierAction(soldier: Soldier): Unit = {
    this.synchronized{
      soldier.getCommand.takeAction(soldier, soldiers, armySize)
      draw()
      Thread.sleep(1)
    }
  }






}
