# SmartPantry

## Description
SmartPantry is an Android application that helps users manage their kitchen pantry.  
Users can add, edit and delete ingredients, track quantities and expiry dates, and view suggested recipes based on available items. The app also includes settings for dark mode and expiry alerts.

## Features
- Add, edit and delete pantry items
- Track quantity, unit and expiry date
- View suggested recipes
- Dark mode support
- Expiry alerts toggle
- Local SQLite database

## Database Choice
I chose **SQLiteOpenHelper** for the following reasons:
- It is lightweight and does not require any external libraries
- It is suitable for a simple local database with two tables (pantry and recipes)
- It gives full control over table creation and seeding of sample data
- It is reliable and easy to implement for a student project of this size

## Setup & Run Instructions

### Requirements
- Android Studio (Hedgehog or newer recommended)
- Minimum SDK 24 (Android 7.0)
- An Android emulator or physical device

### Steps to run the project
1. Clone this repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/SmartPantry.git