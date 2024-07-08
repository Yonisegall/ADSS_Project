# ADSS_Group_AT - Transportation Module

## Submitted by:

- **Yehonatan Segal : 209359801** - Developer
- **Omer Onn : 318910759** - Developer

# Transport Management System

This is a Java-based Transport Management System for managing transport operations, 
including assigning drivers and trucks to deliveries, tracking delivery statuses, 
and managing drivers, trucks, and delivery sites.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Contributors](#contributors)
  
## Installation

1. **Download the JAR file:**
   - Download the JAR file called "adss2024_v02.jar" for the application.

2. **Download the Database file:**
   - Download the `TransportatioDataBase` file.

3. **Setup Directories:**
   - Create a directory named `dev`.
   - Inside the `dev` directory, create another directory named `DB`.
   - Place the `TransportatioDataBase` file inside the `DB` directory.
   - Your directory structure should look like this: dev/DB/TransportatioDataBase
   - Put the jar file and "dev" folder inside of a new folder.
     
4. **Run the JAR file:**
   - Open a terminal or command prompt.
   - Navigate to the directory new folder, containing the JAR file and "dev" folder with the Database.
   - Run the JAR file using the following command: ``` java -jar adss2024_v02.jar.jar ```

## Usage

### Main Menu

Upon running the application, you will see the main menu with the following options:

1. Press "1" if you are a manager.
2. Press "2" if you are a driver.
3. Press "9" to exit.

### Manager Menu

To access the manager menu, enter the password: `123456789`. The manager menu includes the following options:

1. Add Driver
2. Add Truck
3. Add Store
4. Add Supplier
5. Create Transportation
6. Print Transportations.

#### Add Driver

1. Enter the driver's details – name, license level (A, B, C), and an 8-character password.
2. If the details are valid, the driver is added to the driver list (Drivers Repository and DB).

#### Add Truck

1. Enter the truck's details – license plate (8 characters in the format "000-00-000"), license level (A, B, C), net weight, and max weight.
2. If the details are valid, the truck is added to the truck list (Trucks Repository and DB).

#### Add Site (Supplier/Store)

1. Enter the site details – type (Store/supplier), name, address, phone number, contact person, and shipping area.
2. If the details are valid, the site is added to the site list (Sites Repository and DB).
3. If the distribution area does not exist, it will be added to the list of shipping areas.

#### Create Transportation

1. Enter the transportation details – leaving time, date, and source.
2. Select a truck from the available trucks.
3. Select a driver based on the selected truck.
4. Choose a shipping area for the delivery.
5. Choose whether to pick up products from a supplier or deliver products to a store.
6. Select the sites in the chosen shipping area based on the type (store/supplier).
7. Add products to the transportation from the supplier or remove products at the store.
8. Confirm the transportation details and finalize the delivery.

- If the total weight of the delivery is within the truck's weight capacity, the system will confirm that the delivery has been added.
  
- If the total weight exceeds the truck's capacity, a window will open with options for the manager to adjust the delivery:

1. **Change Site**
2. **Replace Truck**
3. **Remove Sites**
4. **Remove Items**

#### Print Transportations

View all deliveries that have ever been made and their statuses (departed, returned, pending).

### Driver Menu

To access the driver menu, enter your name and password. After verification, you will see:

1. A list of assigned deliveries.
2. A message wishing you a safe trip if you are currently on a delivery.
3. A message stating that you are waiting for an assignment if you have no current deliveries.

#### Driver Options

1. Report Leaving
2. Report Back
3. Return to Main Menu

#### Report Leaving

The status of the delivery changes to "Out for Delivery..", and the status of the driver and truck changes to "On the road". The driver and truck cannot be assigned to a new delivery until they return.

#### Report Back

The status of the delivery changes to "Delivered!", and the status of the driver and truck changes to "available". The driver and truck are now available for new deliveries.

#### Drivers list

1. Daniel Levi – 11111111 (Waiting for exit do delivery).
2. Omer Cohen – 22222222.
3. Yoni Avraham – 33333333.
4. Assaf Moalem – 44444444.
5. Hadar Fadida – 55555555.
6. Elad Cohen – 66666666.
7. Noam Revivo – 77777777.
8. Alon Cohen – 88888888.
9. Avraham Peretz – 99999999.
10. Adir Yossef – 10101010.
11. David Segal – 12121212.
12. Noam Kfir – 13131313.
13. Yotam Zimri – 14141414.
14. Noam Fathi – 15151515

## Features

- **User Roles:** Separate menus and functionalities for managers and drivers.
- **Driver Management:** Add and manage drivers with specific license levels.
- **Truck Management:** Add and manage trucks with specific weight capacities.
- **Site Management:** Add and manage delivery sites (suppliers and branches).
- **Delivery Management:** Create, assign, and track deliveries.
- **Status Tracking:** Track the status of deliveries, drivers, and trucks.
- **Validation:** Ensure data integrity with proper validation for all inputs.

## System Requirements

- Java Runtime Environment (JRE) 8 or higher
- The `TransportatioDataBase` file

## Project Structure
```
├── DAL
│   ├── DB_Connector.java
│   ├── IDAO.java
│   ├── DriversDAO.java
│   ├── SitesDAO.java
│   ├── TransportsDAO.java
│   └── TrucksDAO.java
├── DB
│   └── TransportationDataBase.db
├── Domain
│   └── Controller
│       ├── DataController.java
│       ├── DriverController.java
│       ├── SolutionsController.java
│       └── TransportationController.java
│   └── Objects
│       ├── Document.java
│       ├── Driver.java
│       ├── Item.java
│       ├── Site.java
│       ├── Transportation.java
│       ├── TransportDocument.java
│       └── Truck.java
│   └── Repositories
│       ├── IRepository.java
│       ├── DriversRepository.java
│       ├── SitesRepository.java
│       ├── TransportsRepository.java
│       └── TrucksRepository.java
├── Presentation
│   ├── Main.java
│   ├── DriverMenu.java
│   ├── TransportationManagerMenu.java
│   ├── DataConnector.java
│   ├── CreateTransportation.java
│   └── ChooseSolution.java
└── Test
    └── TransportationManagerTests.java

```
#### Dependencies
List the main tools and libraries used in the project.
```
Java: JDK version X.X
JUnit: for testing
SQLite: for database management
Gson: for JSON parsing (com.google.gson.JsonArray, com.google.gson.JsonObject)
java.io: for input and output operations (java.io.*, java.io.BufferedWriter, java.io.ByteArrayOutputStream, java.io.FileWriter, java.io.IOException, java.io.PrintStream)
java.sql: for SQL operations (java.sql.*)
java.util: for utility classes (java.util.*, java.util.ArrayList, java.util.Arrays, java.util.HashMap, java.util.List, java.util.Map, java.util.Objects, java.util.Scanner)
```
## Illustration
Segal and Omer coding all night on their Transportation management system project:
![Segal   Omer](https://github.com/HagaiAstrin/ADSS_Group_AT/assets/143295853/89ec3c5a-c0c1-4e92-8b72-f849d96c3903)
