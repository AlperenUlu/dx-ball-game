# 🎮 DX-BALL (Java Implementation)

A fully implemented DX-BALL (Brick Breaker) game developed in Java using the StdDraw graphics library.  
This project focuses on accurate collision detection, realistic reflection physics, and structured game-state management.

---

## 📌 Overview

DX-BALL is a classic arcade game where the objective is to clear all bricks by bouncing a ball with a paddle.

This implementation includes:

- Precise surface and corner collision handling
- Quadratic-based corner reflection calculations
- Structured two-phase game loop
- Pause system
- Victory & Game Over states
- Modified version with superpowers
- Fully dynamic brick coloring

---

## 🖥 Technologies Used

- Java
- StdDraw (2D graphics & animation)
- Mathematical collision modeling
- Structured game-state design

---

## 🎮 Game Features

### Core Mechanics

- Canvas size: 800x400
- Paddle controlled by LEFT and RIGHT arrow keys
- Press SPACE to start
- Press SPACE again to pause/unpause
- 10 points per brick
- Victory when all bricks are cleared
- Game Over if the ball touches the floor

---

## 🧠 Collision System

This project does not rely on simple bounding-box reflections.  
It includes detailed geometric calculations for realistic behavior.

### 1️⃣ Surface Collision

- Wall reflections (left, right, top)
- Paddle top and side collision
- Brick surface reflections

Velocity components are inverted depending on impact direction.

---

### 2️⃣ Corner Collision (Advanced)

Corner hits are calculated using:

- Euclidean distance
- Reflection angle derived from the normal vector
- Quadratic equation solving
- Portion-based velocity updates

Instead of instantly flipping velocity, the system:

- Computes the exact collision portion of movement
- Adjusts reflection angle
- Applies the remaining velocity after reflection

This prevents clipping and unrealistic bouncing.

---

## 🧮 Physics Model

- Bounce angle depends on paddle contact position
- Both velocityX and velocityY are recalculated dynamically
- Reflection is based on geometry, not hardcoded rules

---

## 🔄 Game Structure

The game runs in two main phases:

### Phase 1 – Pre-Launch
- Paddle positioning
- Trajectory line preview
- Waiting for SPACE to launch

### Phase 2 – Gameplay
- Ball movement
- Collision detection
- Pause state handling
- Victory / Game Over rendering

---

## ⭐ Modified Version – Superpowers

The enhanced version includes:

1. Eliminate 1–5 random bricks  
2. Paddle Width +2  
3. Paddle Width +5  
4. Ball Radius +2  
5. Ball Radius +5  
6. Score x2  

Details:

- Two random powers per game
- Duplicate powers are replaced with Score x2
- Effects tracked with a boolean array
- Only unhit bricks are eliminated
- Brick colors generated randomly each run

---
## 📂 Project Structure

```
dxball/
│
├── code/
│   ├── Main.java              # Standard version of the game
│   └── MainModified.java      # Modified version with superpowers
│
├── report/                    # Project report and documentation
│   └── DXBallGameReport.pdf
│
├── stdlib.jar                 # StdDraw dependency library
│
├── README.md
│
└── .gitignore
```


## ▶ How to Run

1. Make sure `StdDraw.java` is in the same directory.
2. Compile:

```
javac Main.java
```

3. Run:

```
java Main
```

---

## 🎥 Gameplay Videos

Standard Version:  
https://youtu.be/l8tomnZKi6s  

Modified Version:  
https://youtu.be/lkw1vsDKx0k  

---

## 🎯 Key Strengths

- Real mathematical modeling of collisions
- Accurate corner reflection logic
- Structured state-based architecture
- Clean separation of physics and rendering
- No external game engine used

---

## 📈 Learning Outcomes

- 2D collision detection
- Reflection physics modeling
- Quadratic equation applications
- Game loop architecture
- Frame-based animation systems

---

Author: Alperen Ulu
