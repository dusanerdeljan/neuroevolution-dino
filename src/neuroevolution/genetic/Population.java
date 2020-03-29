
package neuroevolution.genetic;

import java.util.ArrayList;
import java.util.List;

import neuroevolution.neuralnetwork.NeuralNetwork;

public class Population {

	public List<Genotype> genomes;
	
	public Population(int populationSize) {
		this.genomes = new ArrayList<Genotype>();
		for (int i = 0; i < populationSize; i++) {
			this.genomes.add(new Genotype());
		}
	}
	
	public void evolve(float elitism, float randomness, float mutationRate, float mutationStdDev, int childCount) {
		this.normalFitnessDistribution();
		this.sortByFitness();
		List<Genotype> nextGeneration = new ArrayList<Genotype>();
		int eliteCount = Math.round(elitism*this.genomes.size());
		for (int i = 0; i < eliteCount; i++) {
			nextGeneration.add(new Genotype(this.genomes.get(i)));
		}
		int randomCount = Math.round(randomness*this.genomes.size());
		for (int i = 0; i < randomCount; i++) {
			NeuralNetwork.FlattenNetwork cNet  = this.genomes.get(0).dino.cactusNet.flatten();
			for (int j = 1; j < cNet.weights.size(); j++) {
				cNet.weights.set(j, (float) (Math.random()*2 - 1));
			}
			NeuralNetwork.FlattenNetwork bNet = this.genomes.get(0).dino.birdNet.flatten();
			for (int j = 1; j < bNet.weights.size(); j++) {
				bNet.weights.set(j, (float) (Math.random()*2 - 1));
			}
			nextGeneration.add(new Genotype(cNet, bNet));
		}
		// Pool selection
		int max = 0;
		while (true) {
			for (int i = 0; i < max; i++) {
				List<Genotype> children = Genotype.breed(this.genomes.get(i), this.genomes.get(max), childCount, mutationRate, mutationStdDev);
				for (Genotype child: children) {
					nextGeneration.add(child);
					if (nextGeneration.size() >= this.genomes.size()) {
						this.genomes = nextGeneration;
						return;
					}
				}
			}
			max++;
			max = max >= this.genomes.size()-1 ? 0 : max;
		}
	}

	private void normalFitnessDistribution() {
		float sum = 0f;
		for (Genotype genome: this.genomes) {
			sum += genome.dino.score;
		}
		for (Genotype genome: this.genomes) {
			genome.fitness = genome.dino.score / sum;
		}
	}
	
	// TODO: Implement quick sort or something else 
	private void sortByFitness() {
		for (int i = 0; i < this.genomes.size()-1; i++) {
			int bestIndex = i;
			for (int j = i+1; j < this.genomes.size(); j++) {
				if (this.genomes.get(j).fitness > this.genomes.get(bestIndex).fitness) {
					bestIndex = j;
				}
			}
			if (bestIndex != i) {
				Genotype temp = this.genomes.get(bestIndex);
				this.genomes.set(bestIndex, this.genomes.get(i));
				this.genomes.set(i, temp);
			}
		}
	}
}
