package bagapps.genetic_algorithm

import scala.collection.mutable.HashBag


object SimpleExample {


  case class IndividualImpl(genes: Vector[Boolean], epochTag: Int) extends Individual {
    lazy val fitness = (genes.count(b => b) * 100).toDouble / genes.size

    override lazy val toString: String = s"${genes.map(if (_) 1 else 0).mkString} [epoch: $epochTag, fitness: $fitness]"
  }

  object IndividualsImpl extends Individuals[IndividualImpl] {

    private val genesSize = 64
    private val mutation = 0.1d

    def crossover(individualA: IndividualImpl, individualB: IndividualImpl, epoch: Int) = IndividualImpl(individualA.genes.take(genesSize / 2) ++ individualB.genes.drop(genesSize / 2), epoch)

    def mutated(individual: IndividualImpl, epoch: Int) = IndividualImpl(individual.genes.map(b => if (Math.random() < mutation) !b else b), epoch)

    def random(epoch: Int): IndividualImpl = IndividualImpl(Vector.fill(genesSize)(Math.random() < 0.5), epoch)

    def compare(x: IndividualImpl, y: IndividualImpl): Int = y.fitness compare x.fitness
  }


  def main(args: Array[String]) {
    GeneticAlgorithm.run(IndividualsImpl, numberIterations = 50, populationSize = 20, elitism = 0.3)
  }
}
