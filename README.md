
# Buiscuit Shop Management System

This is simple POS system created for Buiscuit agency.In this biscuit business, we get new biscuits every day from an agency and distribute them to different shops. 

# Key Features

## Add Shop
The 'Add Shop' button lets you add new shops to system. This is useful when you start distributing biscuits to a new shop. 

## Load Stock
This feature allows you to add new types of biscuits or update the existing stock. For example, if you get a new variety of biscuits or if you need to update the quantity, you can do it using this feature. 

## Add Order
After you sell different kinds of biscuits to a specific shop, you can record the order using this feature.This helps us keep track of what was sold to which shop. 

## Order Details
At the end of the day, you can use this feature to view all the orders made. This gives a clear picture of the day's sales.

# Installation Guide

## Prerequisites

- Java Development Kit (JDK): Ensure you have JDK 8 or higher installed. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/downloads/#java11).

- MySQL Database: Install MySQL Server. You can download it from the [MySQL website](https://dev.mysql.com/downloads/mysql/).

- MySQL Workbench: (Optional) For easier database management, download and install [MySQL Workbench](https://dev.mysql.com/downloads/workbench/).

## Step-by-Step Installation

If you do not have Java installed on your computer please follow below link.
[Java Getting Started](https://www.w3schools.com/java/java_getstarted.asp).

After installaing Java you should install IDE.I recommend Intellij Idea. [Step-by-step guide to install Intellij Idea](https://www.geeksforgeeks.org/step-by-step-guide-to-install-intellij-idea/).

Then you need to import javaFX library to Intellij Idea.
You can download it [here](https://openjfx.io/).

If you got any error related JavaFX please refer [this](https://openjfx.io/openjfx-docs/#IDE-Intellij).

If you hope to edit User Interfaces please install Scene Builder on your pc. [How to install Scene builder in Windows](https://medium.com/@oshadaeraboy/how-to-install-scene-builder-in-windows-and-configure-in-intellij-idea-7291a23a7de7).

Now you have come to the final step.Now you have to setup MySQL.

## Set Up MySQL Database

### Install MySQL Server:
- Follow the installation wizard and configure your MySQL server.
- Set the root password during installation (remember this password).

### Create Database and Tables:

- Open MySQL Workbench or use the MySQL command line.
- Copy and paste [MySQL Script.txt](https://github.com/Imesh-Isuranga/POS4Agency-JavaFX/blob/main/MySQL%20Script.txt).

There is folder name db.Inside that folder there is DBConnection.java class.In here you need to add your MySQL configuration.

```
private DBConnection() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BiscuitAgency", "user", "password");
    }

````

Now you are all set.

Below is some screenshots of system.

## Screenshots

![App Screenshot](https://raw.githubusercontent.com/Imesh-Isuranga/POS4Agency-JavaFX/main/ScreenShots/1.png)

![App Screenshot](https://raw.githubusercontent.com/Imesh-Isuranga/POS4Agency-JavaFX/main/ScreenShots/2.png)









