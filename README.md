📦 Technician Service System – Spring Boot Project
📋 Project Overview
This project is a Spring Boot-based web application for managing service requests between technicians and customers.
It includes features for login, order handling, technician assignment, and automated email notifications.

👥 User Roles
🧑‍💼 Customer
Can sign in and submit a detailed service request.

Upon submission, a confirmation message is displayed, indicating that the request was successfully received and is being processed.

🔧 Technician
Can log in to view all unassigned service requests.

Can choose to claim a request.

After claiming, the technician enters an estimated arrival time.

An automated email is sent to the customer containing:

Technician’s name

Estimated arrival time

Contact details (if applicable)

🔄 (Optional enhancement): Requests may be prioritized based on geographical proximity in future versions.

🛠 Technologies Used
Java 17+

Spring Boot

Spring Data JPA

Spring Security (for login/auth)

Thymeleaf / REST (depending on frontend choice)

MySQL / H2 (for persistence)

JavaMailSender (for email notifications)
