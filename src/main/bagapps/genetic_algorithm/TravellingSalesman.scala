package bagapps.genetic_algorithm

import scala.collection.mutable.HashBag
import scala.collection.mutable

object TravellingSalesman {

  val numCities = 20
  val mutation = 0.3

  val cityLocation = Vector.fill(numCities)(City.random())

  case class City(x: Double, y: Double) {
    def distanceTo(that: City) = Math.sqrt(Math.pow(this.x - that.y, 2) + Math.pow(this.x - that.y, 2))
  }

  object City {
    def random() = City((Math.random() * 100).floor, (Math.random() * 100).floor)
  }

  case class Path(cities: Vector[Int], epochTag: Int) extends Individual {
    lazy val fitness = {
      var length = 0.0
      for (i <- 0 until numCities - 1) {
        val c1 = cityLocation(cities(i))
        val c2 = cityLocation(cities(i + 1))
        length += c1.distanceTo(c2)
      }
      length
    }

    override lazy val toString: String = s"${cities.mkString("(", ",", ")")} [epoch: $epochTag, fitness: $fitness]"
  }

  object Paths extends Individuals[Path] {

    def crossover(pathA: Path, pathB: Path, epoch: Int): Path = {
      var newPath = if (Math.random() < 0.5) pathA.cities.take(numCities / 2) else pathA.cities.drop(numCities / 2)

      for (city <- pathB.cities) {
        if (!newPath.contains(city))
          newPath = newPath :+ city
      }

      Path(newPath, epoch)
    }

    def random(epoch: Int): Path = {
      var cities = List.tabulate[Int](numCities)(n => n)
      var newPath = Vector.empty[Int]

      while (!cities.isEmpty) {
        val index = (Math.random() * cities.size).toInt
        val city = cities(index)
        cities = cities.filter(city != _)
        newPath = newPath :+ city
      }

      Path(newPath, epoch)
    }

    def mutated(path: Path, epoch: Int): Path = {
      if (Math.random() < mutation) {
        val r1: Int = (Math.random() * numCities).toInt
        val r2: Int = (Math.random() * numCities).toInt
        Path(path.cities.updated(r1, path.cities(r2)).updated(r2, path.cities(r1)), epoch)
      } else {
        path
      }
    }

    def compare(x: Path, y: Path): Int = x.fitness compare y.fitness

  }


  def main(args: Array[String]) {
    println("City coordinates: " + cityLocation.mkString("{ ", ", ", " }"))
    GeneticAlgorithm.run(Paths, numberIterations = 200, populationSize = 500, elitism = 0.3)
  }

}
