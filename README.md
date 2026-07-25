# 3D Graphics Simulation (Cube)

A Java project exploring the mathematics behind 3D computer graphics by simulating a cube in 3D space and dynamically changing the camera's viewpoint to create the illusion of rotation.

## Preview
This is a preview of the running simulation
https://github.com/user-attachments/assets/e3c771dd-9913-4e19-9dce-a9a7c9f66cc3

## Overview

This project explores how 3D objects can be represented and rendered on a 2D screen using mathematical transformations.

The cube remains fixed in 3D space while the camera viewpoint rotates around it. The changing perspective is calculated mathematically by transforming the cube's 3D coordinates relative to the camera and projecting them onto a 2D screen.

Rather than using a dedicated 3D graphics engine, the project implements the underlying coordinate transformations and projection calculations manually.

## Technologies

- **Java**
- **Java 2D**
- Linear Algebra
- Trigonometry

## How It Works

The simulation follows a simplified 3D graphics pipeline:

3D Cube Coordinates
        ↓
Camera/View Transformation
        ↓
Perspective Projection
        ↓
2D Screen Coordinates
        ↓
Line Rendering
