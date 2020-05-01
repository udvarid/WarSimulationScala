package Soldier

import Game.Cell

import scala.util.Random

abstract class Command {
  def takeAction(soldier: Soldier, army: List[Soldier], size: Int): Unit

  def moveForward(soldier: Soldier, army: List[Soldier], size: Int): Unit = {
    val occupiedCells = army
      .filter(s => s.liveStatus)
      .map(s => s.cell)
    val possibleCells: List[Cell] = soldier.possibleCells(size).diff(occupiedCells)
    if (possibleCells.nonEmpty) {
      soldier.cell = Random.shuffle(possibleCells).head
      soldier.refreshed()
    }
  }

  def tryToFight(soldier: Soldier, army: List[Soldier], size: Int): Boolean = {
    val enemyCells = army
      .filter(s => s.liveStatus && !s.friend(soldier))
      .map(s => s.cell)

    val possibleCells: List[Cell] = soldier.possibleCells(size).filter(s => enemyCells.contains(s))

    if (possibleCells.nonEmpty) {
      val choosedCell: Cell = Random.shuffle(possibleCells).head
      val enemyOnCell: List[Soldier] = army.filter(s => s.liveStatus && s.cell.equals(choosedCell))

      if (enemyOnCell.nonEmpty) {
        val enemySoldier: Soldier = enemyOnCell.head
        enemySoldier.attacked(soldier)
        return true
      }
    }
    false
  }
}

class FooterCommand extends Command {
  def takeAction(soldier: Soldier, army: List[Soldier], size: Int): Unit = {
    if (!tryToFight(soldier, army, size))
      moveForward(soldier, army, size)
  }


}

class ArcherCommand extends Command {
  override def takeAction(soldier: Soldier, army: List[Soldier], size: Int): Unit = {
    if (!tryToFight(soldier, army, size))
      if (!tryToShoot(soldier, army, size))
        moveForward(soldier, army, size)
  }

  def tryToShoot(soldier: Soldier, army: List[Soldier], size: Int): Boolean = {

    val enemyCells = army
      .filter(s => s.liveStatus && !s.friend(soldier))
      .map(s => s.cell)

    val possibleCells: List[Cell] = soldier.possibleCellsForShoot(size).filter(s => enemyCells.contains(s))

    if (possibleCells.nonEmpty) {
      val choosedCell: Cell = Random.shuffle(possibleCells).head
      val enemyOnCell: List[Soldier] = army.filter(s => s.liveStatus && s.cell.equals(choosedCell))

      if (enemyOnCell.nonEmpty) {
        val enemySoldier: Soldier = enemyOnCell.head
        enemySoldier.attacked(soldier)
        return true
      }
    }
    false
  }
}
