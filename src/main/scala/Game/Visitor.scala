package Game

import Soldier.{Archer, Footer, Knight, Side}
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

trait Visitor {
  def visit(soldier: Footer)

  def visit(soldier: Knight)

  def visit(soldier: Archer)
}

class DrawerVisitor(canvas: Canvas) extends Visitor {
  override def visit(soldier: Footer): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = if (soldier.getSide == Side.North) Color.Green else Color.Red
    gc.fillOval(soldier.cell.x * 25 + 7, soldier.cell.y * 25 + 7, 10, 10)
  }

  override def visit(soldier: Knight): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = if (soldier.getSide == Side.North) Color.Green else Color.Red
    gc.fillRect(soldier.cell.x * 25 + 7, soldier.cell.y * 25 + 7, 10, 10)
  }

  override def visit(soldier: Archer): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = if (soldier.getSide == Side.North) Color.Green else Color.Red
    gc.strokeOval(soldier.cell.x * 25 + 7, soldier.cell.y * 25 + 7, 10, 10)
  }
}
