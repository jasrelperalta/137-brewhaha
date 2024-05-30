# BREW-HAHA

## Overview

Brew-haha is a flappy bird-inspired game set in the mystical realm of witches and magic. Players take on the role of a whimsical witch soaring through the starlit sky on a broomstick. As the night unfolds, players must navigate through a series of challenging obstacles for as long as their competitors are still alive. A point is added per obstacle passed, and the player with most points wins the game.

## Game Mechanics

- Maximum Number of Players
  - The game supports up to four (4) number of players.
- Scoring
  - Players earn one point for each obstacle successfully passed through.
- Winner determination
  - The last player remaining in the game wins the round. All other players are ranked based on their elimination order and final score.
- In-game chat system
  - An in-game chat system allows players to communicate with each other during gameplay. Players can strategize, taunt, or encourage each other as they navigate through the obstacles.

## Team Members

- Jasrel Roby T. Peralta
- Roxanne Ysabel P. Resuello

Lab Section: B-4L

Lab Instructor: Elijah Nicholas Isungga

## HOW TO SETUP

1. Install JAVA SE Development Kit (Java 22)
2. Unzip the 'lib.zip' the JavaFX files to the project in the 'lib' folder (lib folder should be at root folder)
3. Edit the files inside the .vscode folder (launch.json & settings.json) to match your JavaFX path (see below)
4. Run the game using the 'Main.java' file
  
- For #3, Add the following to the 'launch.json' file:
  
  ```json
  
            "vmArgs": "--module-path lib/ --add-modules javafx.controls,javafx.fxml"
  ```

- For #3, Add the following to the 'settings.json' file:

  ```json
  
  {
      "java.project.sourcePaths": [
          "src"
      ],
      "java.project.outputPath": "bin",
      "java.project.referencedLibraries": [
          "lib/javafx-swt.jar",
          "lib/javafx.fxml.jar",
          "lib/javafx.swing.jar",
          "lib/javafx.web.jar",
          "lib/javafx.base.jar",
          "lib/javafx.media.jar",
          "lib/javafx.graphics.jar",
          "lib/javafx.controls.jar"
      ]
  }
  ```

  ## Note
  Link to prior repository: [Old Brewhaha Repo](https://github.com/jasrelperalta/brewhaha)
