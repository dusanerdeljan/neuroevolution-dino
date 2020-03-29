package game.model;

import neuroevolution.neuralnetwork.NeuralNetwork;

public class Dino {
	
	public float x;
	public float y;
	public float width = 60;
	public float height = 120;
	public float velocity = 0.6f;
	public float gravity = 0f;
	public float jumpForce = -16f;
	public boolean isDead = false;
	public float groundLevel;
	public int score = 0;
	public boolean isDucking = false;
	public NeuralNetwork cactusNet;
	public NeuralNetwork birdNet;
	
	private int JUMP = 0;
	private int DUCK = 1;
	private int DO_NOTHING = 2;
	
	public Dino(float x, float y) {
		this.x = x;
		this.y = y;
		this.groundLevel = y;
		//this.net = new NeuralNetwork(6, 10, 3);
		this.cactusNet = new NeuralNetwork(5, 8, 2);
		this.birdNet = new NeuralNetwork(6, 10, 3);
	}
	
	public Dino(float x, float y, NeuralNetwork.FlattenNetwork cNet, NeuralNetwork.FlattenNetwork bNet) {
		this.x = x;
		this.y = y;
		this.groundLevel = y;
		this.cactusNet = NeuralNetwork.expand(cNet);
		this.birdNet = NeuralNetwork.expand(bNet);
	}
	
	public void jump() {
		if (isDucking) {
			this.standUp();
		}
		if ((this.y >= this.groundLevel)) {
			this.gravity = this.jumpForce;	
		}
	}
	
	public void duck() {
		if (this.y >= this.groundLevel && !this.isDucking) {
			float temp = this.width;
			this.width = this.height;
			this.height = temp;
			this.isDucking = true;
		}
	}
	
	public void standUp() {
		if (this.isDucking) {
			float temp = this.width;
			this.width = this.height;
			this.height = temp;
			this.isDucking = false;	
		}
	}
	
	public void update() {
		this.score++;
		this.gravity += this.velocity;
		this.y = Math.min(this.y + this.gravity, this.groundLevel);
	}
	
	public void feed(Obstacle closestObstacle, float distance) {
		if (closestObstacle != null && !this.isDead) {
			if (closestObstacle instanceof CactusObstacle) {
				this.feed((CactusObstacle) closestObstacle, distance);
			} else {
				this.feed((BirdObstacle) closestObstacle, distance);
			}
//			float[] inputs = {
//				distance / 1366,
//				closestObstacle.height / 768,
//				closestObstacle.width / 1366,
//				closestObstacle.y / 768,
//				this.y / 768,
//				Obstacle.VELOCITY / Obstacle.MAX_VELOCITY
//			};
//			int behaviour = this.net.argmax(inputs);
//			if (closestObstacle instanceof BirdObstacle) {
//				BirdObstacle obstacle = (BirdObstacle) closestObstacle;
//				if (obstacle.birdHeight == BirdHeight.HIGH) {
//					if (Math.random() < 0.5) {
//						this.duck();
//					} else {
//						this.standUp();
//					}
//					return;
//				}
//			}
//			if (behaviour == JUMP) {
//				this.jump();
//			} else if (behaviour == DUCK) {
//				this.duck();
//			} else if (behaviour == DO_NOTHING) {
//				this.standUp();
//			}
		}
	}
	
	private void feed(CactusObstacle closestObstacle, float distance) {
		float[] inputs = {
				distance / 1366,
				closestObstacle.height / 768,
				closestObstacle.width / 1366,
				this.y / 768,
				Obstacle.VELOCITY / Obstacle.MAX_VELOCITY
			};
		int argmax = this.cactusNet.argmax(inputs);
		if (argmax == JUMP) {
			this.jump();
		} else {
			this.standUp();
		}
	}
	
	private void feed(BirdObstacle closestObstacle, float distance) {
		float[] inputs = {
				distance / 1366,
				closestObstacle.height / 768,
				closestObstacle.width / 1366,
				closestObstacle.y / 768,
				this.y / 768,
				Obstacle.VELOCITY / Obstacle.MAX_VELOCITY
			};
		int argmax = this.birdNet.argmax(inputs);
		if (argmax == JUMP) {
			this.jump();
		} else if (argmax == DUCK) {
			this.duck();
		} else if (argmax == DO_NOTHING) {
			this.standUp();
		}
	}
}
