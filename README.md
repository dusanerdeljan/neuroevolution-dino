# neuroevolution-dino
Using artificial neural networks and genetic algorithm to train bot to play Chrome Dino game.

<img src="resources/screenshot.png" width="100%">

## Neural network architecture

Each dino in the population has 2 neural networks:

 * Neural network responsible for avoiding cactuses - 5 inputs, 8 hidden and 2 output neurons. Inputs to this neural network are: 
    * Horizontal distance to the next obstacle
    * Height of the next obstacle
    * Width of the next obstacle
    * Dino's y position
    * Game speed
    
    Net has 2 outputs - Dino will jump if the first output is greater than the second.
    
 * Neural network responsible for avoiding the birds - 6 inputs, 10 hidden and 3 output neurons. Inputs to this neural network are: 
    * Horizontal distance to the next obstacle
    * Height of the next obstacle
    * Width of the next obstacle
    * Obstacle's y position
    * Dino's y position
    * Game speed
    
    Net has 3 outputs - jump, duck, nothing.

## Genetic algorithm options

 * Population size: 100
 * Elitism: 0.2
 * Random behaviour: 0.2
 * Mutation rate: 0.1
 * Mutation standard deviation: 0.5
 * Number of children: 1
 
### Neural network encoding

Each neural network flattens to one-dimensional array of weights. First elements of the array are the weights connecting input layer with the first hidden layer and so on.

### Crossover

Child firstly takes all the weights from one of its parents. For every weight of the child's network, there is a 50% chance for it to be replaced with the corresponding weight of the second parent's network.

### Selection

Selection algorithm used is Pool selection.

## Neural network visualization

 * Weight of the line indicates how strong the net weight is
 * Blue means weight is positive
 * Red means weight is negative

## Graphics library

Java Processing 3.5.4: https://processing.org/

## License

This program is free.</br>
You can redistribute it and/or change it under the terms of **GNU General Public License version 3.0** (GPLv3). </br>
You can find a copy of the license in the repository.
