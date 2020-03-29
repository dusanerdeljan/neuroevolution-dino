package game.model;

public class BirdObstacle extends Obstacle {

	public enum BirdHeight {
		LOW, MEDIUM, HIGH;
	}
	
	public BirdHeight birdHeight;
	public int state;
	
	public BirdObstacle(float x, float y, BirdHeight birdHeight) {
		super(x, y);
		this.type = ObstacleType.BIRD;
		this.birdHeight = birdHeight;
		this.width = 100;
		this.height = 40;
		switch (birdHeight) {
		case LOW:
			this.y -= 20;
			break;
		case MEDIUM:
			this.y -= 60;
			break;
		case HIGH:
			this.y -= 170;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void update() {
		super.update();
		this.state ^= 1;
	}
}
