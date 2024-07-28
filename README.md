# IOT_Finals
This proyect consist in create a product using IOT. In other words a dog food dispenser. 

# Final Networks Project: Intelligent Food Dispenser

## Description
This project aims to monitor and dispense food for your pet, providing real-time updates on feeding schedules and automatically dispensing food when the bowl is empty. The system ensures your pet is well-fed even when you are not around, making it an intelligent food dispenser.

The system leverages a Telegram bot to interact with users, providing commands to check the status of the food bowl and dispense food. Additionally, it uses an MQTT client to receive data from sensors, which monitor the weight of the food bowl and the distance from the sensor to the bowl.

## How It Works
### System Components
1. **Telegram Bot**: The bot handles user interaction through predefined commands, enabling users to start the service, check the food bowl status, and get feeding history.
2. **MQTT Client**: Connects to an MQTT broker to receive sensor data, which includes the weight of the food bowl and the distance measurement. This data is processed to decide when to dispense more food.

### Data Flow
1. **Sensor Data**: The sensors measure the distance from the sensor to the food bowl and the weight of the food in the bowl. This data is sent to the MQTT broker.
2. **MQTT Client**: The client subscribes to relevant topics on the MQTT broker to receive sensor data. When data is received, it parses the information and updates the state of the system.
3. **Telegram Bot Interaction**: Users interact with the bot using commands. The bot provides responses based on the latest sensor data and can initiate food dispensing if certain conditions are met (e.g., the bowl is empty).

### Commands and Functionalities
- **/start**: Initializes the bot and provides a welcome message along with the current time.
- **/estado**: Checks the current status of the food bowl. It tells the user whether the bowl is empty or shows the current weight of the food.
- **/comida**: Displays the last feeding time and duration, if available.


## Project Structure
### The project is organized into the following main components:

1. **Telegram Bot (Bot.java)**:
- **Purpose**: Manages interaction with the user through Telegram.
- **Functions**:
  - Receives commands from users.
  - Sends responses based on the current state of the food dispenser.
  - Interfaces with the MQTT client to get the latest sensor data.

  
2. **Main Application (Main.java)**:
- **Purpose**: Initializes the bot and manages the MQTT client connection.
- **Functions**:
    - Registers the Telegram bot with the Telegram API.
    - Connects to the MQTT broker.
    - Subscribes to the sensor data topic.
    - Processes incoming sensor data and updates the bot state accordingly.

  
3. **Maven Configuration (pom.xml)**:
- **Purpose**: Manages project dependencies and build configuration.
- **Dependencies**:
  - org.telegram:telegrambots: Library for creating Telegram bots.
  - com.hivemq:hivemq-mqtt-client: MQTT client for connecting to the broker and handling sensor data.



