# Vehicle Maintenance Scheduler

## Overview
This project is a Spring Boot backend service that schedules vehicle maintenance tasks across multiple depots.  
It optimizes task selection using the **0/1 Knapsack Algorithm** to maximize impact within available mechanic hours.

---

## Features
- Logging Middleware using Spring Interceptor
- External API Integration (Depots & Vehicles)
- Token-based Authentication (Bearer Token)
- Optimized Scheduling using Dynamic Programming
- Clean Layered Architecture (Controller → Service → Model)
- REST API Endpoint for schedule generation

---

## Tech Stack
- Java
- Spring Boot
- REST APIs
- Maven
- Jackson (JSON Mapping)

---

## How It Works

1. Fetch depots from external API
2. Fetch vehicle tasks (duration + impact)
3. For each depot:
   - Apply **0/1 Knapsack Algorithm**
   - Select tasks within mechanic hours
   - Maximize total impact
4. Return optimized schedule

---

## API Endpoint

### GET `/schedule`

#### Response Example:
```json
[
  {
    "depotId": 1,
    "vehicles": [
      {
        "taskId": "abc123",
        "duration": 3,
        "impact": 7
      }
    ]
  }
]
