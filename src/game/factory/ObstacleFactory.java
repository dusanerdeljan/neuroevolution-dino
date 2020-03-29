package game.factory;

import game.model.Obstacle;

public class ObstacleFactory {
	
	private static float birdObstacleChance;
	
	private ObstacleFactory() {
		
	}
	
	public static void init(float birdChance, float x, float y) {
		birdObstacleChance = birdChance;
		BirdFactory.init(x, y);
		CactusFactory.init(x, y);
	}
	
	public static Obstacle getObstacle() {
		if (Math.random() < birdObstacleChance) {
			return BirdFactory.getBird();
		} else {
			return CactusFactory.getCactus();
		}
	}
}
