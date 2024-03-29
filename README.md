# DeliveryAPI

This project is made to showcase my knowledge and undestanding of Java Spring framework and my ability to combine technologies to make an API.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [API Endpoints](#api-endpoints)
- [Technologies Used](#technologies-used)
  
## Introduction

This project is made to represent a delivery service fee calculation module. Based on weather data provided from the goverment weather reports, the API can calculate the delivery in three specified cities: Tallinn, Tartu, Pärnu.

## Features

- Pulling data from the government weather station endpoint [https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php]  
- Keeping a database of specific locations and data  
- Providing REST endpoints for calculating a delivery fee based on location and weather data  

## Installation

Step 1: Clone the repository  
Step 2: Open the project in IntelliJ  
Step 3: Run the DeliveryApiApplication class  


## API Endpoints

The structure of the endpoints
![image](https://github.com/1nnu/deliveryAPI/assets/76624399/9dca3fab-1447-42cb-810e-35b8e3a5270e)

Available cities: tallinn, tartu, parnu  
Available vehicles: car, scooter, bike  
Structure of request: (domain)/delivery/{city}/{vehicle}  

## Technologies Used

- Framework: Java Spring  
- Technologies: H2 database,   Spring WEB,   Lombok,   jackson-dataformat-xml
