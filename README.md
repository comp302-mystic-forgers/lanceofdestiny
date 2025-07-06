# Lance Of Destiny

## 🎮 Introduction

Lance of Destiny is an interactive game where you control a magical staff balancing a fireball to destroy barriers and reach the legendary lance. Each barrier has unique traits—some are rewarding, others are deadly. You have only **one life**. To win, destroy all obstacles!

---

## 🚀 How to Run the Game

1. **Download** the zip file and open the code in your IDE.
2. Open `GameController.java` and run it to start the game.
3. The **Welcome Page** will appear.
4. Click **Start Game** to open the Login Page.
5. **Sign up** or **log in** to an existing account. You can load an old game (feature coming soon) or start a new one.
6. If starting a new game, choose your barrier numbers and click **Play**.
7. Enjoy! Avoid dropping the ball and reach the lance with your life to win!

---

## 🏗️ Class Structure

The game is implemented with **31 different classes** for modularity and clarity.

---

## 🎁 Bonus Feature

- **Collect Gift:**  
  When you hit a rewarding barrier, you receive a gift!

---

## 📝 Feature List

### 1. Starting The Game

#### 1.1 GameController, BuildingModeController & GameView

- `GameController` is the entry point for the desktop version.
- Sets up the game window and launches the game using the Swing framework.
- Delegates screen management to `BuildingModeController` during setup, and to `GameWindow` during play.
- On first run, opens the `WelcomePage` with a start button, background image, and BGM.

#### 1.2 Login, Database (MongoDB) & Building Mode Menu

- Clicking "Start Game" opens the Login Page.
- Log in to your account (saved in the online database) or sign up with a new username and password (must meet length requirements).
- After login, access the Building Mode Menu to load an old game (coming soon) or create a new one.

#### 1.3 Building Mode (Assembly of Barriers)

- Choose the amount of each barrier type.
- Rendering is handled by `GameLayoutPanel`.

##### Barrier Types

- **SimpleBarrier:** Breaks with one hit.
- **RewardingBarrier:** Drops a gift!
- **ReinforcedBarrier:** Hard to destroy, requires 1-5 hits.
- **ExplosiveBarrier:** Falls when hit; colliding with it means game over.

---

### 2. Playing The Game

#### 2.1 Render Fireball, Magical Staff, and Barriers

- Fireball checks for collisions with the staff or barriers and reflects accordingly.

#### 2.2 Display HUD

- The HUD includes a pause option.

#### 2.3 Moving the Character

- The magical staff starts in the middle.
- Move left/right with arrow keys, rotate with A/D.
- Movement is limited to the bottom of the screen.

#### 2.4 Collision with Traps & Enemies

- Colliding with an explosive barrier or dropping the ball ends the game.

#### 2.5 Losing the Game

- If you die, the game ends and a "Game Over" message is shown.

#### 2.6 Winning the Game

- Destroy all barriers to win!

---

### 3. Pausing the Game

- Press the Pause button to stop the game.
- All elements freeze and the pause screen appears.
- Options:
  1. Resume
  2. Quit
  3. Exit to Menu
  4. Help (tips)

---

### 4. Screen Adjustment

- Default screen is not fullscreen.
- You can adjust the screen size; zoom remains the same, but only part of the map is visible.
- HUD adapts to different sizes.

---

## 👥 Contributors

Created by:  
Agne Armonaite, Emircan Kılıç, Ertuğrul Altuntaş, Ferhat Özen, Gülbeyaz Baymaz, Igor Cvijanović


