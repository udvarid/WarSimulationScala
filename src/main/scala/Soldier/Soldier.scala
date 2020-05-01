package Soldier

import Game.{Cell, Visitor}

import scala.annotation.tailrec

trait CanShoot {
  def shoot(enemy: Soldier)
}

trait CanHit {
  def hit(enemy: Soldier)
}

object Side extends Enumeration {
  val North, South = Value
}

abstract class Soldier(range: Int = 1,
                       canHitBack: Boolean = false,
                       hitValue: Int,
                       private var myLife: Int,
                       side: Side.Value,
                       command: Command) {

  def getHitValue: Int = hitValue

  def friend(otherSoldier: Soldier): Boolean = side.equals(otherSoldier.getSide)

  def getSide: Side.Value = side

  def getCommand: Command = command

  private var myCell: Cell = Cell(0, 0)

  def cell: Cell = myCell

  def cell_=(newCell: Cell) = myCell = newCell

  def life: Int = myLife

  private var fresh: Boolean = true

  def isItFresh: Boolean = fresh

  def exhausted(): Unit = fresh = false

  def refreshed(): Unit = fresh = true

  def attacked(enemy: Soldier, close: Boolean = true): Unit = {
    if (enemy.getHitValue >= myLife) {
      myLife = 0
      live = false
    }
    else {
      myLife -= enemy.getHitValue
      if (canHitBack && isItFresh && close) {
        exhausted()
        enemy.attacked(this)
      }
    }
  }

  private var live: Boolean = true

  def liveStatus: Boolean = live

  def accept(visitor: Visitor)

  @tailrec
  private def cellCreator(index: Int, cells: List[Cell], border: Int, refCell: Cell): List[Cell] = {
    if (index > refCell.x + 1) cells
    else {
      if (index >= 0 && index < border)
        cellCreator(index + 1, Cell(index, if (side == Side.South) refCell.y - 1 else refCell.y + 1) :: cells, border, refCell)
      else
        cellCreator(index + 1, cells, border, refCell)
    }
  }

  def possibleCells(border: Int): List[Cell] = {
    cellCreator(myCell.x - 1, List[Cell](), border, myCell)
  }

  def possibleCellsForShoot(border: Int): List[Cell] = {
    val newCell = Cell(myCell.x, if (side == Side.South) myCell.y - 1 else myCell.y + 1)
    cellCreator(myCell.x - 1, List[Cell](), border, newCell)
  }

}

case class Footer(hitValue: Int = 3, myLife: Int = 7, command: Command, side: Side.Value)
  extends Soldier(hitValue = hitValue, myLife = myLife, command = command, side = side) with CanHit {
  def hit(enemy: Soldier): Unit = enemy.attacked(this)

  def accept(visitor: Visitor): Unit = visitor.visit(this)
}


case class Knight(hitValue: Int = 5, myLife: Int = 10, command: Command, side: Side.Value)
  extends Soldier(hitValue = hitValue, myLife = myLife, canHitBack = true, command = command, side = side)
    with CanHit {
  override def hit(enemy: Soldier): Unit = enemy.attacked(this)

  override def accept(visitor: Visitor): Unit = visitor.visit(this)
}


case class Archer(hitValue: Int = 2, myLife: Int = 5, command: Command, side: Side.Value)
  extends Soldier(hitValue = hitValue, myLife = myLife, range = 2, command = command, side = side)
    with CanShoot with CanHit {
  override def shoot(enemy: Soldier): Unit = enemy.attacked(this, close = false)

  override def hit(enemy: Soldier): Unit = enemy.attacked(this)

  override def accept(visitor: Visitor): Unit = visitor.visit(this)
}

