
# SaloonNemo

## Project Overview
SaloonNemo is a comprehensive salon reservation system designed to streamline the booking process for salons and their clients. This system allows clients to easily book appointments online, while providing salon staff with an efficient way to manage these reservations. The project is developed using Java and other supporting technologies.

## Features
- **User Registration and Login**: Secure registration and login system for both clients and salon staff.
- **Appointment Booking**: Clients can view available time slots and book appointments with their preferred stylist.
- **Appointment Management**: Salon staff can view, confirm, and manage appointments.
- **Service Listings**: Detailed listings of all services offered by the salon, including descriptions and pricing.
- **Notification System**: Email notifications for appointment confirmations, reminders, and cancellations.
- **Admin Dashboard**: A comprehensive dashboard for administrators to manage staff, services, and appointments.

## Getting Started
### Prerequisites
Before you begin, ensure you have met the following requirements:
- You have installed Java Development Kit (JDK) version 8 or higher.
- You have a MySQL database setup.

### Installation
To install SaloonNemo, follow these steps:
1. Clone the repository
    ```bash
    git clone https://github.com/Thiwain/individual-viva-SaloonNemo.git
    ```
2. Navigate to the project directory
    ```bash
    cd individual-viva-SaloonNemo
    ```
3. Set up the database:
    - Create a new MySQL database.
    - Import the database schema from the provided SQL file.
4. Update the database configuration in the project:
    - Modify the database connection settings in the `config` file.
5. Build the project using Apache Ant:
    ```bash
    ant build
    ```

## Usage
To use SaloonNemo, follow these steps:
1. Start the application server:
    ```bash
    ant run
    ```
2. Open your web browser and navigate to `` to access the system.

## Running Tests
To run tests, use the following command:
```bash
ant test
```

## Contributing
To contribute to SaloonNemo, follow these steps:
1. Fork this repository.
2. Create a branch: 
    ```bash
    git checkout -b <branch_name>
    ```
3. Make your changes and commit them: 
    ```bash
    git commit -m '<commit_message>'
    ```
4. Push to the original branch: 
    ```bash
    git push origin <branch_name>
    ```
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://help.github.com/articles/creating-a-pull-request/).

## License
This project is licensed under the [License Name] License - see the LICENSE file for details.

## Contact
If you want to contact me, you can reach me at [thiwainm@gmail.com].

---
