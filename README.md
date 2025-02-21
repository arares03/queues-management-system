# Queue Management System


# ![QMS](https://github.com/user-attachments/assets/8b708587-b53a-4795-b562-2bcd2d39911e)

# 1\. Assignment Objective

The goal is to develop a queue-based simulation that models client arrivals, queue entry, waiting times, service times, and departures.


# 2\. Problem Analysis & Use Cases

Functional Requirements

Configure simulation parameters (clients, queues, arrival/service times, duration, selection policy).

Start and visualize queue evolution in real-time.

Track client status (waiting, served, completed).

Display key metrics: waiting time, service time, peak hours.

Non-Functional Requirements

User-friendly, responsive UI.

Efficient memory and processing optimization for large simulations.

Modular, well-documented, maintainable code.

# Use Cases

Setup Simulation: User inputs parameters → System validates → Simulation starts.

Start Simulation: Clients are randomly generated → Queues update in real-time.

View Metrics: System calculates and displays average waiting time, service time, and peak hours.



# 3\. Design & Implementation

Architecture Overview

MVC Pattern: Separates UI (view), business logic (controller), and data processing (model).

Multi-threading: Uses separate threads for client handling and server processing.

# Class Structure

📂 Controller Class



Handles UI actions, starts the simulation, and validates inputs.

📂 SimulationFrame Class (GUI)



Collects user inputs, displays queues and statistics.

📂 Scheduler Class



Manages task scheduling using Shortest Queue or Shortest Time policies.

📂 SimulationManager Class



Generates tasks, simulates queue processing, and computes statistics.

📂 Server Class



Processes tasks in a queue, tracks waiting times.

📂 Task Class



Represents client tasks with arrival and service times.

📂 Strategy Classes



ShortestQueue: Assigns tasks to the queue with the fewest clients.

ShortestTime: Assigns tasks to the queue with the shortest processing time.

# Simulation Process

1️⃣ Initialize Clients & Queues → Clients assigned random arrival & service times.

2️⃣ Process Tasks → Clients enter queues based on scheduling policy.

3️⃣ Compute Metrics → Track waiting time, service time, peak hours.

4️⃣ Display Results → UI updates in real-time, logs saved to display.txt.

# 4\. Results & Conclusions

The simulation successfully models real-time queue management.

Metrics help analyze system efficiency under different conditions.

Future improvements: more scheduling policies, dynamic UI enhancements, cloud-based deployment.
