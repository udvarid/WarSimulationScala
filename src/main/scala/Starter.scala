import Game.Game
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas

object ScalaFXHelloWorld extends JFXApp {
  val size: Int = 750
  val canvas = new Canvas(size, size)

  stage = new PrimaryStage {
    title = "Drawing Operations Test"
    scene = new Scene {
      content = canvas
    }
  }

  val game = new Game(size / 25, canvas)
  game.draw()
  println("Starting game")
  game.startGame()





}
