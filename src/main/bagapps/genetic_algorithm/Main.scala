package bagapps.genetic_algorithm

import scala.collection.immutable
import scala.collection.immutable.{TreeBag => Population}
import scala.annotation.tailrec

object Main {

  val populationSize = 20
  val genesSize = 8
  val numberIterations = 10
  val elitism = 0.5d
  val mutation = 0.1d

  def main(args: Array[String]) {

    @tailrec
    def geneticAlgorithm(population: Population[Individual], iteration: Int): Population[Individual] = {
      println(s"Iteration $iteration".padTo(15, ' '))
      println("  best fitness: " + (1d - population.head.fitness.toDouble / genesSize) * 100 + "%")
            println(population + "  "+ population.size)

      if (iteration < numberIterations) {

        val numberElites = (elitism * populationSize).toInt
        // FIXME: bug with .take method of bag
        val bestPopulation = population.take(numberElites)

        val numberCrossover = (populationSize - numberElites) / 2

        val newGeneration = Population.newBuilder[Individual]

        for (_ <- 1 to numberCrossover) {
          val individualA = selectRandom(population)
          val individualB = selectRandom(population)
          val individualC = crossover(individualA, individualB)
          newGeneration += mutated(individualC)
        }

        val newPopulation = bestPopulation union newGeneration.result()


        geneticAlgorithm(newPopulation, iteration + 1)
      }
      else population
    }

    val bestPopulation = geneticAlgorithm(randomPopulation, 0)

    println("Best individual: " + bestPopulation.head)

  }


  case class Individual(genes: Vector[Boolean]) {
    lazy val fitness = genes.count(b => !b)

    override lazy val toString: String = genes.map(if (_) 1 else 0).mkString
  }

  implicit object Individual extends Ordering[Individual] {
    def compare(x: Individual, y: Individual): Int = x.fitness compare y.fitness
  }

  implicit val m = immutable.BagBucketFactory.Sorted.ofBagBucketBag[Individual]

  def randomPopulation = {
    val b = Population.newBuilder[Individual]
    for (_ <- 1 to populationSize)
      b += Individual(Vector.fill(genesSize)(Math.random() < 0.5))
    b.result()
  }

  def selectRandom(population: Population[Individual]) = {
    population.toList((Math.random() * population.size).toInt)
  }

  def crossover(individualA: Individual, individualB: Individual) = new Individual(individualA.genes.take(genesSize / 2) ++ individualB.genes.drop(genesSize / 2))

  def mutated(individual: Individual) = Individual(individual.genes.map(b => if (Math.random() < mutation) !b else b))

}
