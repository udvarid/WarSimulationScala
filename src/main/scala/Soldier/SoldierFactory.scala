package Soldier

import Game.Cell

import scala.annotation.tailrec

object SoldierFactory {

  def createArmy(size: Int): List[Soldier] =
    createArcher(size) ++ createFooter(size) ++ createKnight(size)

  private def createArcher(size: Int): List[Archer] = {
    val command: ArcherCommand = new ArcherCommand
    @tailrec
    def produce(row: Int, side: Side.Value, produced: List[Archer]) : List[Archer] = {
      if (produced.size == size) produced
      else {
        val archer: Archer = Archer(command = command, side = side)
        archer.cell = Cell(produced.size, row)
        produce(row, side, archer :: produced)
      }
    }
    produce(0, Side.North, List[Archer]()) ++
      produce(size - 1, Side.South, List[Archer]())
  }

  private def createFooter(size: Int): List[Footer] = {
    val command: FooterCommand = new FooterCommand
    @tailrec
    def produce(row: Int, side: Side.Value, produced: List[Footer]) : List[Footer] = {
      if (produced.size == size) produced
      else {
        val footer: Footer = Footer(command = command, side = side)
        footer.cell = Cell(produced.size, row)
        produce(row, side, footer :: produced)
      }
    }
    produce(2,  Side.North, List[Footer]()) ++
      produce(size - 3, Side.South, List[Footer]())
  }

  private def createKnight(size: Int): List[Knight] = {
    val command: FooterCommand = new FooterCommand
    @tailrec
    def produce(row: Int, side: Side.Value, produced: List[Knight]) : List[Knight] = {
      if (produced.size == size) produced
      else {
        val knight: Knight = Knight(command = command, side = side)
        knight.cell = Cell(produced.size, row)
        produce(row, side, knight :: produced)
      }
    }
    produce(1,  Side.North, List[Knight]()) ++
      produce(size - 2, Side.South, List[Knight]())
  }

}
