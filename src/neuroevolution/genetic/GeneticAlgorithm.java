
package neuroevolution.genetic;

import java.util.List;

import game.factory.DinoFactory;
import game.model.Dino;
import game.model.Obstacle;

public class GeneticAlgorithm {
	
	public class ObstacleInfo {
		public float distance;
		public Obstacle closestObstacle;
		
		public ObstacleInfo(float distance, Obstacle closestObstacle) {
			this.distance = distance;
			this.closestObstacle = closestObstacle;
		}
	}

	public Population population;
	public int alive;
	public int generation;
	
	public int populationSize = 100;
	public float elitism = 0.2f;
	public float mutationRate = 0.1f;
	public float mutationStdDev = 0.5f;
	public float randomness = 0.2f;
	public int childCount = 1;
	
	private Dino bestGenome;
	
	public GeneticAlgorithm() {
		this.population = new Population(this.populationSize);
		this.bestGenome = this.population.genomes.get(0).dino;
		this.alive = this.populationSize;
		this.generation = 1;
	}
	
	public void updatePopulation(List<Obstacle> obstacles) {
		ObstacleInfo data = this.getClosestObstacle(obstacles);
		for (Genotype genome: this.population.genomes) {
			if (!genome.dino.isDead) {
				genome.dino.feed(data.closestObstacle, data.distance);
				genome.dino.update();
			}
		}
	}
	
	public void evolvePopulation() {
		this.alive = this.populationSize;
		this.generation++;
		this.population.evolve(this.elitism, this.randomness, this.mutationRate, this.mutationStdDev, this.childCount);
		this.bestGenome = this.population.genomes.get(0).dino;
	}
	
	public Dino getBestGenome() {
		return this.bestGenome;
	}
	
	public int getBestScore() {
		int best = 0;
		for (Genotype genome: this.population.genomes) {
			if (genome.dino.score > best) {
				best = genome.dino.score;
			}
		}
		return best;
	}
	
	public boolean populationDead() {
		for (Genotype genome: this.population.genomes) {
			if (!genome.dino.isDead) {
				return false;
			}
		}
		return true;
	}
	
	private ObstacleInfo getClosestObstacle(List<Obstacle> obstacles) {
		Obstacle closestObstacle = null;
		float distance = Float.MAX_VALUE;
		for (Obstacle obstacle: obstacles) {
			distance = obstacle.x - DinoFactory.getSpawnX();
			if (distance > 0) {
				closestObstacle = obstacle;
				break;
			}
		}
		return new ObstacleInfo(distance, closestObstacle);
	}
}
