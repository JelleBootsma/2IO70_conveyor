# 2IO70-Conveyor Java Implementation

This repository contains a Java implementation of the conveyor belt driver for the TU/e course 2IO70, developed by 

- J.H. Bootsma
- T.P.H. Hoeijmakers
- W. van Oorschot
- C.P.A. Aarts
  
The conveyor communicates using the protocol as described in [2IO70-COM](https://github.com/JelleBootsma/2IO70-COM). The protocol version used in this implementation is V1.1.

## Overview
The conveyor belt system is designed to transport black or white disks between multiple bots. These bots perform various operations such as taking an item from the belt, placing an item on the belt, and detecting the sequential order of disks using a bot equipped with a light detector
 
The conveyor belt driver is responsible for controlling the conveyor belt system and facilitating communication between bots. It utilizes MQTT (Message Queuing Telemetry Transport), a lightweight M2M (Machine to Machine) or IoT protocol, for sending and receiving signals between bots. The main focus of MQTT is data integrity and reliability, making it a suitable choice for communication in our robot system.

## Specifications

### Channels

MQTT uses channels for transmitting and receiving signals. In this implementation, each bot has its own set of channels. The naming convention for the channels is as follows:

- Bot Channels: `Bot[group number]_[T/R]`

The `[group number]` represents the specific group number assigned to each bot. The `T` indicates that the bot uses this channel to transmit data to the broker, while `R` indicates that the bot uses this channel to receive data from the broker.

Additionally, there are two special channels, `Control_R` and `Control_T`, for communication between the MQTT broker and the control system (conveyor belt).

It's important to note that each group should only use its specified channels and should not communicate with other channels.

### Signals

The conveyor belt driver supports the following signals that can be sent and received over the MQTT channels:

Signals that can be received by the conveyor belt:

- `emergency`: Sent by a bot when it detects an error and wants the entire system to go into emergency mode.
- `available`: Sent by a bot (Group 3 or 4) to the system once it has finished its task and is ready for a new one.
- `placeItem`: Sent by a bot when it wants to place an item on the belt. The bot will either receive a `placeItemGranted` or `placeItemDenied` signal in response.
- `sequenceReceived`: Sent by a bot (Group 2) when it has received all disks of its predefined sequence.

Signals that can be sent by the conveyor belt:

- `takeItem`: Received by a bot when it should take exactly one disk from the belt. The disk will arrive in approximately 2 seconds. This signal is sent only when the bot has indicated it is available.
- `reboot`: Received by a bot in an emergency situation, indicating a system reboot. All bots should go back to their initial state.
- `placeItemGranted`: Received by a bot (Group 3 or 4) when its request to place an item has been granted. The bot should place the item on the belt within 3 seconds.
- `placeItemDenied`: Received by a bot (Group 3 or 4) when its request to place an item has been denied. This can occur during a sequence when the conveyor belt needs to be clean.
- `startSequence`: Sent by the system at most once per 2 minutes when it is ready to provide a predefined sequence.

Note that initially, all bots will be considered free, so there is no need to send an available signal at the boot of the system.
