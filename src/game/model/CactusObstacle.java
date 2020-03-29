package game.model;

public class CactusObstacle extends Obstacle {
	
	public enum CactusType {
		SMALL, MEDIUM, LARGE
	};
	
	public CactusType cactusType;

	public CactusObstacle(float x, float y, CactusType cactusType) {
		super(x, y);
		this.type = ObstacleType.CACTUS;
		this.cactusType = cactusType;
		switch (this.cactusType) {
		case SMALL:
			this.width = 40;
			this.height = 60;
			break;
		case MEDIUM:
			this.width = 100;
			this.height = 80;
			break;
		case LARGE:
			this.width = 140;
			this.height = 100;
		default:
			break;
		}
	}
}
