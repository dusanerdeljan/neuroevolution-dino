package game.factory;

import game.model.Dino;
import neuroevolution.neuralnetwork.NeuralNetwork;

public class DinoFactory {

	private static float spawnX;
	private static float spawnY;
	
	
	private DinoFactory() {
		
	}
	
	public static void init(float x, float y) {
		spawnX = x;
		spawnY = y;
	}
	
	public static Dino getDino() {
		return new Dino(spawnX, spawnY);
	}
	
	public static Dino getDino(NeuralNetwork.FlattenNetwork cNet, NeuralNetwork.FlattenNetwork bNet) {
		return new Dino(spawnX, spawnY, cNet, bNet);
	}
	
	public static float getSpawnX() {
		return spawnX;
	}
}
