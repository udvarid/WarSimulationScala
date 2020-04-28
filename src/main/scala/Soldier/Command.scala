package Soldier

abstract class Command {
  def takeAction(soldier: Soldier): Unit
}

class FooterCommand extends Command {
  override def takeAction(soldier: Soldier): Unit = ???
}

class KnightCommand extends Command {
  override def takeAction(soldier: Soldier): Unit = ???
}

class ArcherCommand extends Command {
  override def takeAction(soldier: Soldier): Unit = ???
}
