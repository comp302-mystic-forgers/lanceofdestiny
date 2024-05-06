# Lance Of Destiny


## Introduction
Lance of Destiny is an interactive game where you will play and navigate a magical staff that balances a fireball which destroys barriers to reach the lance of destiny.
Each barrier has different traits. Some are rewarding, others can kill you. You only have 1 live.
In order to win, you need to find destroy all obstacles.


## How to run the game
1. You should download the zip file and upload the code to your IDE. Open the GameController and run it to start.
2. You will see the welcome page.
3. Click "start game" to open the Login Page.
4. Either sign up or login to an existing account. You will then be able to load an old game (soon to be added feature) or play a new one.
5. If you decide to play a new one choose your barrier number. Click play.
6. Play the game! Avoid dropping the ball and reach the lance with your live to win the game!

## Class Structure
In order to implement this game, we set up 31 different classes.

## Bonus Feature
1. Collect Gift
   When you hit a rewarding barrier you get a gift.

## Feature List
### 1. Starting The Game
#### 1.1 GameController, BuidlingModeController & GameView
The GameController class is the entry point for the desktop version of the game.
It sets up the game window and launches the game using Swing framework.
First it delegates the Screen Management to the BuidlingModeController during the Building (Set up) Mode, and then to the GameWindow during Running (Playing) Mode.

When running the Lance Of Destiny for the first time, it will open WelcomePage screen, which displays a button to start the game, background image and play the BGM.

#### 1.2 Login, Database (MongoDB) & Building Mode Menu
When player click the "start game" button, player will open a Login Page.
Player needs to log in to his account saved in the online DataBase or sign up with new username and password meeting length requirements.
Once logged in, player will go to Building Mode Menu. In Building Mode Menu he/ she can load an old game (soon feature implementation) or create a new game.

#### 1.3 Building Mode (Assembly of Barriers)
Player needs to choose the amount of each barrier type included.

##### Value & Type
 SimpleBarrier -> Only needs to be touched once by Fireball to break \
 RewardingBarrier -> This barrier drops a gift!\
 ReinforcedBarrier -> The barrier is hard to destory and has between 1 and 5 hits duration \
 ExplosiveBarrier -> This obstacle falls down when hit, and you loose your live when colliding with one of them \
 

### 2. Playing The Game
#### 2.1 Render Fireball, Magical Staff and Barriers
Fireball checks if collision with magical staff or barriers and gets reflected.


#### 2.2 Display HUD
The HUD consists of Pause option.

#### 2.3 Moving the Character
Player (magical staff) will start from middle of screen.
Player can move the character using arrow buttons (left, right) during the game and rotate with A and D.
Player can move around the bottom but cannot move up.

#### 2.4 Collision with Trap & Enemies
When player collides with the explosive barrier or ball drops to the bottom, then player's dies!

#### 2.5 Losing the Game
When player's dies, then player loses and the game ends.
We will show "Game Over" message.


#### 2.6 Win the Game
When player destroys all barriers, then player wins and the game ends.


### 3. Pausing the Game
Player can press Pause button to pause the game,
When the game pauses, then every element of the game will stop and the pause screen is shown.
There are four options in the pause screen:
1. Resume Button - to continue the game
2. Quit Button - to stop the game completely
3. Exit Button - to go back to menu screen
4. Help Butoon - to open Help Screen with tips

### 4. Screen Adjustment
Player have the default screen that is not in full screen.
Player has the ability to adjust screen size. The zoom will not be affected, but only certain part of the maps can be shown.
HUD will always be shown in different sizes.

### Contributors

Created by Agne Armonaite, Emircan Kılıç, Ertuğrul Altuntaş, Ferhat Özen, Gülbeyaz Baymaz and Igor Cvijanović


