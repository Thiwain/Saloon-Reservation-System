---

# SaloonNemo

## Overview
SaloonNemo is an advanced, user-friendly salon reservation system designed to simplify the booking process for both salon clients and staff. Developed in Java, the system provides a seamless way for clients to book appointments online and for salons to manage these reservations efficiently. The application is built with a responsive design that ensures usability across different devices, including desktops, tablets, and mobile phones.

## Key Features

### 1. User Registration and Login
- Provides a secure and intuitive registration and login process for both clients and salon staff.
- Passwords are encrypted to ensure high security and data protection.
- Users can easily reset passwords and manage their account information.
- Supports multiple user roles (e.g., client, stylist, administrator) with role-based access control.

### 2. Appointment Booking
- Clients can view available time slots, choose their preferred stylist, and book appointments with just a few clicks.
- The system supports both single appointments and recurring bookings, providing flexibility for regular clients.
- Real-time availability updates prevent double-booking and ensure accuracy.
- Clients receive immediate confirmation of their booking, with details of the appointment and stylist.

### 3. Appointment Management
- Salon staff can access a comprehensive appointment management interface to view, confirm, reschedule, or cancel appointments.
- The interface includes filtering options to sort appointments by date, stylist, or service type, making it easier for staff to manage their schedules.
- An integrated calendar view allows for a visual representation of all appointments, improving overall appointment management efficiency.
- Administrators have additional capabilities to oversee all appointments across multiple stylists and services.

### 4. Service Listings
- The system provides detailed information about all services offered by the salon, including descriptions, pricing, duration, and any specific stylist expertise.
- Services are categorized for easier navigation by clients, such as “Haircuts,” “Coloring,” “Styling,” etc.
- Service details are dynamically updated, allowing administrators to make changes to service offerings without requiring a system restart.

### 5. Notification System
- The application automatically sends email notifications to clients and staff for various events, such as appointment confirmations, reminders, and cancellations.
- Notification preferences can be configured by both clients and staff to control the frequency and type of notifications received.
- The system supports custom email templates to maintain consistent branding and communication style.

### 6. Admin Dashboard
- A comprehensive admin dashboard provides tools for administrators to manage salon operations, staff, services, and appointments.
- Features include analytics and reporting tools for monitoring key business metrics, such as appointment trends, client retention, and revenue performance.
- Administrators can add, edit, or remove services, manage user roles, and control access rights within the system.
- The dashboard is designed with a modern interface that allows for easy navigation and data visualization.

### 7. File Caching Mechanism
- Implements a local file caching system to store frequently accessed data from the MySQL database in an XML format.
- This reduces the load on the server by minimizing database requests, thereby improving application performance and response times.
- The caching mechanism is configurable, allowing administrators to set cache duration and refresh intervals as needed.

### 8. Logging System
- The application uses a dual logging system:
  - **Database Logger**: Captures and records all interactions with the database, such as data queries, updates, and deletions.
  - **Local Logger**: Logs errors, user activities, and general information to a local log file, which is useful for debugging and auditing.
- Administrators can view and manage log files directly from the Admin Dashboard.

### 9. Theme Switching
- Users can switch between light and dark themes based on their preferences.
- The selected theme is saved locally and automatically applied the next time the user logs in, ensuring a personalized experience.
- The application uses responsive design principles, ensuring that the interface adapts seamlessly to different screen sizes and devices.

### 10. Responsive Design
- SaloonNemo is built with a fully responsive design, making it compatible across various devices, including desktops, tablets, and smartphones.
- The user interface follows modern design trends, providing an intuitive and visually appealing experience for all users.

## Getting Started

### Prerequisites
- **Java Development Kit (JDK) version 8 or higher**: Required to run the application.
- **MySQL Database**: A MySQL database should be set up either locally or on a cloud service like AWS Lightsail.

### Installation and Setup

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/Thiwain/individual-viva-SaloonNemo.git
    ```

2. **Navigate to the Project Directory:**
    ```bash
    cd individual-viva-SaloonNemo
    ```

3. **Run the Application:**
   - You can run the application by executing the provided JAR file:
    ```bash
    java -jar SaloonNemo.jar
    ```

   - Alternatively, if you want to build the project from the source, use Apache Ant:
     ```bash
     ant build
     ```

### Usage

1. **Start the Application:**
   - Execute the JAR file or use Apache Ant to run the server.

2. **Access the Application:**
   - Open your web browser and navigate to `http://localhost:8080` (or the server’s IP address if hosted externally) to use SaloonNemo.

### Running Tests

- To run tests, use the following command:
    ```bash
    ant test
    ```
- The tests cover core functionalities such as user registration, appointment booking, and notification systems. They also include integration tests to ensure smooth interaction between different modules.

## Contributing

To contribute to SaloonNemo:

1. **Fork the repository.**
2. **Create a new branch** for your feature or bug fix:
    ```bash
    git checkout -b <branch_name>
    ```
3. **Make your changes** and commit them:
    ```bash
    git commit -m 'Add your commit message here'
    ```
4. **Push to your branch**:
    ```bash
    git push origin <branch_name>
    ```
5. **Open a pull request** and describe your changes.

## License
This project is licensed under the [MIT License](LICENSE).

## Contact

For any questions or feedback, you can reach out to me at [thiwainm@gmail.com].

---
