# Risk-Game
This project is using the MVC architecture design. The structure separates into 3 main parts for the system, called Controller, Model, and View.

### Controller: 
For the design of the Controller, we create 3 different classes inside the package called MainController.class, GameController.class, MapEditorController.class to deal with the control for the main menu, gameplay, and the map editor. <br/>
  Each of the class focusses on the specific area of the features. <br/>

### Model:
Packages: GamePlay, Map, Utilities <br/>
The model package mainly focuses on the implementation of the logic functions of the gaming and editing part. <br/>
In the GamePlay package, we implement Army.class for the army battles. Card.class for getting and trading cards. The Player.class for data changes of each player. <br/>
The Map package contains classes like Continent.class, Country.class, Map.class, and MapEditor.class. <br/>
The first two classes deal with the basic data and logic for two main map properties in the game. <br/>
The Map class includes functions for the data change, saving, and loading on the map. <br/>
The functions in MapEditor class can let users create, editing, and save map files. <br/>

Utilities package includes useful classes that can be widely reused by other classes. <br/>
Such as FileHandler class for reading and writing files. Message class for showing messages to the user. <br/>
Random class dealing with random index values. The StringAnalyzer class focus on the string function logic. 
		
### View:
Packages: Common, GamePlay, MapEditor <br/>
View mainly deals with functions that making the functions in other parts can be easily viewed by users. <br/>
The Common package helps to show the main menu to help users to choose whether they want to edit the map or play the game. <br/>
The gamePlay package helps users to choose map files and implementing the game state phases for users to know what is happening when playing the game. <br/>
The MapEditor class fulfill functions for showing the map properties to help users create and editing maps. <br/>

![Image of MVC](https://github.com/Bill1119/Risk-Game/blob/master/images/image01.png)
