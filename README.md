# Hotel OOP1

A desktop hotel-management application developed for the **Object-Oriented Programming 1** course at the Faculty of Technical Sciences, University of Novi Sad. The application is written in Java and uses a Swing graphical user interface, object-oriented domain entities, manager classes for application logic, and CSV files for local data storage.

## Project Overview

The system models the day-to-day work of a hotel. It supports several user roles, reservation processing, room and equipment management, guest and employee records, pricing, additional services, housekeeping assignments, check-in/check-out, and operational reports.

The application starts with a login screen. After authentication, each user is directed to the interface available to their role:

- **Administrator**: manages employees, rooms, room types, equipment, additional services, and price lists. Administrators can also review reports and display charts for revenue, reservation statuses, and housekeeper workload.
- **Receptionist**: adds guests, reviews and processes reservation requests, performs check-in and check-out operations, and views room information.
- **Guest**: creates reservations by room type or by number of guests, selects additional services and requested equipment, views personal reservations and total costs, and cancels eligible reservations.
- **Housekeeper**: views assigned rooms and updates room-cleaning information through the housekeeping workflow.

## Main Features

### Authentication and roles

Users log in with a username and password stored in the corresponding CSV file. The login process recognizes administrators, receptionists, housekeepers, and guests and opens the appropriate role-specific user interface.

### Hotel data management

The application provides managers and UI screens for maintaining:

- guests and their reservations;
- administrators, receptionists, and housekeepers;
- rooms, room types, room status, and room equipment;
- additional services;
- price lists and their validity periods;
- base salary data and employee information.

### Reservations

Guests can search for available room types for a date range and create a reservation based on:

- a selected room type; or
- the required number of guests, with room capacity and requested equipment taken into account.

Reservations contain the guest, dates, number of guests, room type, selected services, requested equipment, total price, status, assigned room, and assigned housekeeper when applicable. Reservation statuses include pending, confirmed, rejected, in progress, completed, and cancelled. Pending reservations whose arrival date has passed are automatically rejected when reservation data is loaded.

### Reception workflow

Receptionists can filter and process pending reservations, assign rooms, handle arrivals and departures, and inspect current room availability. The application separates check-in and check-out operations so reservation status and room status can be updated as the guest journey progresses.

### Reports and charts

Administrators can open reports for:

- income and expenses;
- rooms assigned to housekeepers;
- confirmed reservations;
- processed reservations;
- the current room overview.

Charts are available for revenue by room type over the previous 12 months, housekeeper workload over the previous 30 days, and reservation statuses over the previous 30 days. Charts are generated with XChart.

## Technology Stack

- **Java 17**
- **Java Swing** for the desktop user interface
- **CSV files** for persistence
- **JUnit 4** for unit tests
- **JDatePicker 1.3.4** for date selection
- **XChart 3.8.8** for reports and charts
- **Eclipse** project configuration included in the repository

## Repository Structure

```text
Hotel_OOP1/
├── README.md
└── projekat_sv_48_2023/
	├── src/
	│   ├── entity/       Domain classes and enums
	│   ├── formater/      Date formatting helpers
	│   ├── main/          Application entry point
	│   ├── manage/       Managers, persistence, and business logic
	│   ├── model/        Swing table models
	│   ├── unitTests/    JUnit test suite
	│   └── view/         Swing windows and user interfaces
	├── data/              CSV data files used by the application
	├── img/               Icons and other UI images
	├── lib/               Third-party JAR files
	└── bin/               Eclipse compiler output
```

### Domain model

The `entity` package contains the principal domain objects, including `Gost`, `Zaposleni`, `Soba`, `TipSobe`, `Rezervacija`, `Cenovnik`, `DodatnaUsluga`, and `Oprema`. Enums represent values such as room status, reservation status, gender, job position, and education level.

### Application logic

The `manage` package contains manager classes responsible for loading, searching, creating, editing, deleting, and saving domain objects. Most managers read from and write to a dedicated CSV file in the `data` directory. `RezervacijaManager` coordinates reservations with guests, rooms, room types, services, equipment, and housekeepers.

### Presentation layer

The `view` package contains the Swing windows. The `model` package contains table models used to display domain data in `JTable` components. The interface and application labels are primarily in Serbian.

## Data Storage

The application uses local CSV files instead of a database. The files are located in `projekat_sv_48_2023/data/`:

| File | Stored data |
| --- | --- |
| `administratori.csv` | Administrator accounts and employee data |
| `recepcioneri.csv` | Receptionist accounts and employee data |
| `sobarice.csv` | Housekeeper accounts and employee data |
| `gosti.csv` | Guest accounts and guest data |
| `rezervacije.csv` | Reservations and their current state |
| `sobe.csv` | Rooms, status, type, equipment, and assignments |
| `tipoviSoba.csv` | Room types and capacities |
| `oprema.csv` | Available room equipment |
| `dodatneUsluge.csv` | Additional hotel services |
| `cenovnik.csv` | Price lists and prices by validity period |
| `osnovice.csv` | Base salary information |

Because paths are relative, the application should be launched with `projekat_sv_48_2023` as the working directory. This allows references such as `data/gosti.csv` and `img/logout.png` to resolve correctly.

## Running the Application in Eclipse

1. Install a Java 17 JDK and Eclipse with Java development tools.
2. Import the `projekat_sv_48_2023` directory as an **Existing Eclipse Project**.
3. Confirm that the project uses JavaSE-17. The included `.classpath` already references the `src` directory, the `bin` output directory, JUnit 4, and the JAR files in `lib`.
4. Set `src/main/Main.java` as the run configuration's main class.
5. Set `projekat_sv_48_2023` as the working directory.
6. Run the project. The login window should appear.

The `Main` class opens `LoginUI`. The commented setup code in `Main.java` contains example manager operations for creating and modifying sample data, but it is not required for normal application startup.

## Running Tests

The unit tests are in `src/unitTests/`. `AllTests.java` groups the manager tests into one JUnit 4 suite, covering administrators, pricing, additional services, guests, equipment, salary bases, receptionists, reservations, rooms, housekeepers, room types, and employees.

In Eclipse, right-click `src/unitTests/AllTests.java` and select **Run As > JUnit Test**.

## Course Context

This project was created for the second semester of the Software Engineering and Information Technologies program as a practical exercise in object-oriented design, inheritance, encapsulation, collections, file persistence, GUI development, and unit testing.
Projekat za predmet Objektno orijentisano programiranje 1.
