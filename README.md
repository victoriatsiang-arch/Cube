# 3D Graphics Simulation (Cube)

A Java project exploring the mathematics behind 3D computer graphics by simulating a cube in 3D space by calculating the points of a cube rotating in the xz plane. 

## Preview
This is a preview of the running simulation
<video src="https://github.com/user-attachments/assets/68628f85-0991-40ff-bf1e-d87cbed9c3eb" width="100%" controls muted autoplay></video>

## Overview

This project explores how 3D objects can be represented and rendered on a 2D screen using mathematical transformations.

Currently, the cube's coordinates are recalculated using matrix multiplication. The camera and eye movement stay still. 
- used idea of grey code when generating and connecting the lines the vertices of the cube together 

Past commits showcase a bit of rotation by moving the coordinates of the screen and keeping the cube stationary. In The changing perspective is calculated mathematically by transforming the cube's 3D coordinates relative to the camera and projecting them onto a 2D screen.

Rather than using a dedicated 3D graphics engine, the project implements the underlying coordinate transformations and projection calculations manually.

## Technologies

- **Java**
- **Java 2D**
- Linear Algebra
- Trigonometry

## How It Works

The simulation follows a simplified 3D graphics pipeline:

3D Cube Coordinates Transformation
        ↓
Perspective Projection
        ↓
2D Screen Coordinates
        ↓
Line Rendering
