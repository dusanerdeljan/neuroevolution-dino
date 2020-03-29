package game.model;

public class BirdObstacle extends Obstacle {

	public enum BirdHeight {
		LOW, MEDIUM, HIGH;
	}
	
	public BirdHeight birdHeight;
	public int state;
	
	private int counter = 5;
	
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
		this.counter--;
		if (this.counter <= 0) {
			this.state ^= 1;
			this.counter = 5;
		}
	}
}
