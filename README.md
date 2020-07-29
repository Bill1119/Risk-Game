# Risk-Game
This project is using the MVC architecture design. The structure separates into 3 main parts </br>
for the system, called Controller, Model, and View.

## How to Run:
This program was written using Java and can be executed with Java jdk 13. </br>
Just use java to run the MainController.java then the program will start. </br>
The test cases is based on JUnit 4.

### 1. Controller: 
In this project, it has 4 controllers, GameController, MainController, MapEditorController, and TournamentController. <br/>

#### 1.1 MainController:
In the MainController, it is the menu model of the system when first launched, it gives </br>
players 2 options at the beginning, either play game or edit map.

#### 1.2 GameController:
In the GameController, there is one method, excute, to perform the whole phase of the gameplay, </br>
including startup phase, reinforcement phase, attack phase and fortification phase. 

#### 1.3 MapEditorController:
This controller will be called when the player choose to edit map. Any operation related to map </br>
editing, will be implemented in this controller.

#### 1.4 TournamentController:
This controller will be called when the player choose the tournament mode. In this mode, the player can </br>
choose 1 to 5 maps, how many games will play in each map, and 2 to 4 AI players. Once the player finished <br/>
choosing those options, the mode will run the input and show the final result for each map and game to the user.

### 2. Model:
Model is the component where all the data related logic respond to, the model will manage all behavior and </br>
data during the whole process of the application.

#### 2.1 GamePlay:
In the GamePlay package, we created classes and their related attributes that will be </br>
needed during the game, which includes card, dices, phase and player.

#### 2.2 Strategy:
Strategy is a sub-package of the GamePlay package. This package is dealing with strategy </br>
classes for players. It include 4 AI strategy classes such as Aggressive AI, Benevolent AI, </br>
Random AI, and Cheater AI. Human class in this package is dealing with the human player’s </br>
actions. The ConcreteStrategy class is for the basic functions that needed to be used for other </br>
classes in this package. The Strategy class is for the combine of four strategies for players </br>
and easy to let programmer to import the strategy classes.

#### 2.3 Map:
The Map package contains classes like Continent.class, Country.class, Map.class, MapEditor.class, </br>
and MapChecker.class. The first two classes deal with the basic data and logic for two main map  </br>
properties in the game. The Map class is one of the most important class in the project, </br>
since no operation can run without map, it carries the most functions among other classes, </br>
which includes functions for checking validation of maps, data change, saving, and loading maps, </br>
set country armies and so on. The functions in MapEditor class can let users create, editing, and save map files. </br>
The MapChecker class is mainly for checking whether if a map passes all the tests, and to determine if it is playable or not.

#### 2.4 Utilities:
Utilities package includes useful classes that can be widely reused by other classes. Such as FileHandler class </br>
for reading and writing files. Rng class dealing with random index values. </br>
The StringAnalyzer class focus on the string function logic. 
		
### 3. View:
View is the component that interact directly with players and model, acting as an interface </br>
to show players the information and possible option they can choose.

#### 3.1 Common:
The Common package contains the most generalized views in the game, which includes View, MainMenuView, MapSelectionView.</br>
View is an abstract class which contains the input validation. MainMenuView is the main menu interface </br>
MapSelectionView is the interface when player is selecting maps.

#### 3.2 GamePlay:
The gamePlay package contains all interfaces during the game play, which includes startup,</br>
reinforcement, attack, fortification, phase change, card exchange, map viewing, world domination view, winner view.

#### 3.3 MapEditor:
The MapEditor view class fulfill functions for showing the map properties to help users create </br>
and editing maps. It includes 3 view classes such as EditorMenuView, EditView, and MapEditorView.</br>

![Image of MVC](https://github.com/Bill1119/Risk-Game/blob/master/images/image01.png)
</br>


