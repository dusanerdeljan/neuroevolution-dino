
package neuroevolution.genetic;

import java.util.ArrayList;
import java.util.List;

import game.factory.DinoFactory;
import game.model.Dino;
import neuroevolution.neuralnetwork.NeuralNetwork;

public class Genotype {

	public Dino dino;
	public float fitness;
	
	public Genotype() {
		this.dino = DinoFactory.getDino();
		this.fitness = 0;
	}
	
	public Genotype(NeuralNetwork.FlattenNetwork cNet, NeuralNetwork.FlattenNetwork bNet) {
		this.dino = DinoFactory.getDino(cNet, bNet);
		this.fitness = 0;
	}
	
	public Genotype(Genotype genome) {
		this.dino = DinoFactory.getDino(genome.dino.cactusNet.flatten(), genome.dino.birdNet.flatten());
		this.fitness = 0;
	}
	
	public static List<Genotype> breed(Genotype male, Genotype female, int childCount, float mutationRate, float mutationStdDev) {
		List<Genotype> children = new ArrayList<Genotype>();
		for (int ch = 0; ch < childCount; ch++) {
			NeuralNetwork.FlattenNetwork childCactusNet = male.dino.cactusNet.flatten();
			NeuralNetwork.FlattenNetwork childBirdNet = male.dino.birdNet.flatten();
			NeuralNetwork.FlattenNetwork parentCactusNet = female.dino.cactusNet.flatten();
			NeuralNetwork.FlattenNetwork parentBirdNet = female.dino.birdNet.flatten();
			childCactusNet = breedNeuralNetworks(childCactusNet, parentCactusNet, mutationRate, mutationStdDev);
			childBirdNet = breedNeuralNetworks(childBirdNet, parentBirdNet, mutationRate, mutationStdDev);
			children.add(new Genotype(childCactusNet, childBirdNet));
		}
		return children;
	}
	
	private static NeuralNetwork.FlattenNetwork breedNeuralNetworks(NeuralNetwork.FlattenNetwork childNet, NeuralNetwork.FlattenNetwork parentNet, float mutationRate, float mutationStdDev) {
		for (int i = 0; i < childNet.weights.size(); i++) {
			if (Math.random() <= 0.5) {
				childNet.weights.set(i, parentNet.weights.get(i));
			}
		}
		for (int i = 0; i < childNet.weights.size(); i++) {
			if (Math.random() <= mutationRate) {
				childNet.weights.set(i, (float) Math.random()*2*mutationStdDev - mutationStdDev);
			}
		}
		return childNet;
	}
}
