# 🎮 DX-BALL Game (Java – StdDraw)

A physics-accurate **DX-BALL (brick breaker)** game implemented in **Java** using the **StdDraw** graphics library.  
This project focuses on **realistic collision mechanics**, **vector-based reflections**, and **robust corner-collision handling**, going beyond basic arcade implementations.

---

## 📌 Project Overview

DX-BALL is a classic brick-breaking game where the player clears all bricks by bouncing a ball using a paddle.  
The game operates in a **zero-gravity environment** with a **constant-speed ball**, where direction changes are determined purely by **physics-based reflections**.

- **Win Condition:** Destroy all bricks  
- **Lose Condition:** Ball touches the bottom of the screen  

---

## 🎥 Gameplay Videos

- ▶ **Standard Version**  
  https://youtu.be/18tqnnZKj6s

- ▶ **Modified Version (with Superpowers)**  
  https://youtu.be/tkw1vsDKxok

---

## 📄 Full Technical Report

All mathematical derivations, collision proofs, diagrams, and implementation details are explained step-by-step in the report:

📘 **DXBallGameReport.pdf**

The report includes:
- Vector-based reflection derivations  
- Quadratic equation for collision portion calculation  
- Corner collision diagrams  
- Bug analysis and fixes for brick collision ordering  

---

## 🧠 Core Game Mechanics

### 🎯 Aiming & Launch System
- Adjustable shooting angle before launch
- Red trajectory line visualizes the initial direction
- Velocity components are calculated using:
  - `vx = v · cos(θ)`
  - `vy = v · sin(θ)`

---

### 🧱 Brick System
- 44 bricks, each worth **10 points**
- Maximum score: **440**
- Each brick:
  - Has center-based coordinates
  - Uses half-width / half-height geometry
  - Is tracked individually with a hit-status array

---

### 🏓 Paddle Mechanics
- Controlled via **Left / Right arrow keys**
- Boundary-restricted movement
- Supports:
  - Top surface collisions
  - Side collisions
  - **Corner collisions with angular reflection**

---

## ⚙️ Physics & Collision Handling

### 🔁 Wall & Surface Collisions
- Flat surface collisions reflect the ball by negating velocity components:
  - Vertical surface → `vx = -vx`
  - Horizontal surface → `vy = -vy`
- Position correction prevents penetration into surfaces.

---

### 📐 Corner Collision Physics (Advanced)

This project implements **true corner collision handling**, unlike basic DX-BALL clones.

#### Key Steps:
1. Corner proximity is detected using Euclidean distance:
   distance(ballCenter, corner) ≤ ballRadius
2. The **portion of the velocity vector** that entered the object is calculated
using a quadratic equation.
3. The penetrating portion is subtracted from the ball position.
4. Reflection angle is computed using:
- `θ` → direction of motion
- `φ` → normal vector angle
5. New velocity vector is calculated and applied.

This guarantees:
- No tunneling
- No missed corner collisions
- Physically correct bounce angles

All derivations are shown graphically and mathematically in the report.

---

### 🧩 Brick Corner Collision Bug Fix

**Problem:**  
Bricks were processed from top-left to bottom-right, causing right and bottom
corner collisions to be incorrectly treated as surface hits.

**Solution Implemented:**
- Corner collisions are checked **before** brick removal
- Adjacent bricks are detected
- If two bricks share a corner:
- Collision is treated as a surface collision
- Both bricks are removed
- Score increases accordingly

This significantly improves collision accuracy and realism.

---

## ⏸ Game States

- **Pre-launch:** Aim adjustment
- **Playing:** Real-time physics simulation
- **Paused:** Space key toggles pause
- **Game Over:** Ball hits bottom
- **Victory:** All bricks destroyed

Each state has its own rendering logic and UI feedback.

---

## 🧪 Modified Version – Superpowers

The modified version introduces dynamic gameplay mechanics.

### ⭐ Available Superpowers
1. Randomly eliminate **1–5 bricks**
2. Paddle width ×2
3. Paddle width +5
4. Ball radius ×2
5. Ball radius +5
6. Score ×2

### 🧠 Design Details
- Two powers are randomly selected per game
- Duplicate selections are prevented
- Each power:
- Has a visual indicator
- Activates on ball contact
- Can be used only once

Brick elimination always targets **unhit bricks** to ensure fairness.

---

## 🛠 Compilation & Execution

Compile the project:
```bash
javac *.java
```

Run with input and output files:
```bash
java Main
```
Make sure StdDraw.java is available in the same directory or classpath.

---

## 📚 Technologies Used

- Java
- StdDraw Graphics Library (`stdlib.jar`)
- Vector Mathematics
- Euclidean Geometry
- Physics-Based Collision Detection
- Procedural Game Loop Design

---

## 📁 Repository Structure

```bash
dxball/
├── .idea/
├── code/
│   ├── .idea/
│   ├── Main.java
│   └── MainModified.java
├── out/
├── report/
├── dxball.iml
└── stdlib.jar
```

---

## 📌 Repository Notice

This repository contains the **full implementation** of the DX-BALL project.  
It is made public **for educational and demonstration purposes only**.

---

## ⚠️ Usage and License

This project is provided **strictly for educational and demonstration purposes**.

- ❌ Reuse of the code for academic submissions is **not permitted**
- ❌ Modification and re-submission for course credit is **not allowed**
- ❌ Plagiarism or direct reuse violates academic integrity policies
- ✅ The code may be reviewed to understand:
  - Physics-based collision handling
  - Game loop design
  - Vector reflection mechanics
  - Robust corner collision solutions

By accessing this repository, you agree to use the material **only as a learning reference**.

---

## 🏁 Final Remarks

This project prioritizes:
- Mathematical correctness
- Physically accurate reflections
- Robust collision detection
- Clean separation of game states

For full understanding of the implementation,  
📘 **reading the report** and  
🎥 **watching the gameplay videos**  
are strongly recommended.


