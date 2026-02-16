# 🎮 DX-BALL (Java – Physics-Based Brick Breaker)

A Java implementation of the classic **DX-BALL / Brick Breaker** game built using the `StdDraw` graphics library.

This project focuses on:

- Accurate surface & corner collision detection
- Real geometric reflection modeling
- Structured game-state architecture
- Physics-driven bounce mechanics
- Extended version with superpowers

---

## 🎥 Gameplay Videos

- ▶ Standard Version: https://youtu.be/l8tomnZKi6s  
- ▶ Modified Version: https://youtu.be/lkw1vsDKx0k  

---

## 📄 Full Technical Report

📘 **report/DXBallGameReport.pdf**

The report includes:

- Detailed explanation of all variables
- Step-by-step breakdown of collision logic
- Quadratic formula derivation for corner reflection
- Distance-based reflection model
- Bug fix explanation for corner–surface conflict
- Game loop structure analysis
- Superpower system explanation
- Screenshots of gameplay states

---

## 🎯 Game Goal

Destroy all bricks by bouncing the ball using the paddle.

### Win Condition
- All bricks are removed → **Victory**

### Lose Condition
- Ball touches the floor → **Game Over**

---

## ⌨️ Controls

- ← → : Move paddle
- SPACE : Start game
- SPACE : Pause / Resume

---

## 🧠 Game Architecture

The game is structured around two main gameplay phases:

### Phase 1 – Pre-Launch
- Paddle positioning
- Aiming trajectory preview
- Waiting for SPACE to launch the ball

### Phase 2 – Gameplay Loop
- Ball movement update
- Collision detection
- Score update
- Pause state handling
- Victory / Game Over rendering

---

## 🧮 Physics & Collision System

This project does **not** rely on simple bounding-box flips.

It includes advanced collision modeling:

---

### 1️⃣ Surface Collisions

Handled for:

- Left wall
- Right wall
- Ceiling
- Paddle (top + sides)
- Brick surfaces

Velocity reflection logic:

- Horizontal surface → invert `velocityY`
- Vertical surface → invert `velocityX`

---

### 2️⃣ Corner Collision (Advanced Geometric Handling)

Corner impacts are computed using:

- Euclidean distance formula
- Normal vector calculation
- Angle derivation using `atan`
- Quadratic equation solving
- Portion-based velocity splitting

Instead of instantly flipping velocity:

1. The system calculates collision portion of movement.
2. Reflection angle is computed.
3. Remaining velocity is applied after reflection.
4. Final velocity vector is reconstructed.

This prevents:
- Clipping inside bricks
- Unrealistic bounce artifacts
- Diagonal removal bugs

---

### 3️⃣ Collision Priority Fix

A key improvement:

If a brick corner is detected first but
a surface collision is actually dominant,
the engine prioritizes correct surface resolution.

This prevents missed bottom/right brick removals.

---

## ⭐ Modified Version – Superpowers

The modified version introduces six power-ups:

1. Eliminate 1–5 random bricks  
2. Paddle Width +2  
3. Paddle Width +5  
4. Ball Radius +2  
5. Ball Radius +5  
6. Score x2  

Details:

- Two random powers per gameplay
- Duplicate powers replaced with Score x2
- Effects tracked with a boolean array
- Only unhit bricks are eliminated
- Brick colors generated randomly each run

---

## 📂 Repository Structure

```bash
dxball/
├── .idea/
├── code/
│   ├── Main.java              # Standard version
│   └── MainModified.java      # Superpower version
├── report/
│   └── DXBallGameReport.pdf   # Full technical explanation
├── stdlib.jar                 # StdDraw dependency
├── dxball.iml
└── README.md
```

---

## 🛠 How to Run

### 1️⃣ Make sure `stdlib.jar` is present.

### 2️⃣ Compile

```bash
javac -cp .:stdlib.jar code/Main.java
```

(Windows users should replace `:` with `;`)

### 3️⃣ Run

```bash
java -cp .:stdlib.jar code.Main
```

For modified version:

```bash
java -cp .:stdlib.jar code.MainModified
```

---

## 🎨 Rendering & Visuals

- Canvas: 800x400
- Bricks stored in 2D coordinate array
- Dynamic brick color generation
- On-screen score + angle indicator
- Pause / Victory / Game Over screens rendered using StdDraw

---

## 📈 What This Project Demonstrates

- 2D collision detection
- Geometric reflection modeling
- Quadratic equation usage in motion systems
- State-based game loop architecture
- Physics-driven arcade mechanics
- Clean separation between rendering & logic

---

## 📌 Repository Notice

This repository contains the full implementation
and is shared strictly for educational purposes.

It is intended to demonstrate:

- Collision modeling
- Reflection mathematics
- Structured Java game architecture

---

## ⚠️ Usage Policy

This project is provided **for learning and demonstration only**.

- ❌ Reuse for academic submission is not permitted
- ❌ Direct copying violates academic integrity
- ✅ Code may be reviewed to understand physics modeling and architecture

---

## 🏁 Final Remarks

This project transforms a simple brick breaker
into a mathematically structured physics system.

Key highlights:

- Real geometric corner reflection
- Portion-based velocity handling
- Corrected collision-priority logic
- Clean two-phase game architecture
- Expandable superpower system

For full understanding:
- 📘 Read the report
- 🎥 Watch gameplay
- 🧠 Explore the code

