# Technician Service System – Spring Boot Project

## Project Overview

This project is a Spring Boot-based web application for managing service requests between **technicians** and **customers**.  
It includes functionality for:

- User login (customers and technicians)
- Submitting and managing service requests
- Technician assignment
- Email notifications to customers

---

## User Roles

### Customer

- Can sign in and submit a detailed service request.
- Upon submission, a confirmation message is displayed, indicating the request was successfully received and is being processed.

### Technician

- Can log in to view all **unassigned service requests**.
- Can choose to **claim** a request.
- After claiming, the technician provides an **estimated arrival time**.
- The system sends an automated email to the customer with:
  - Technician’s name
  - Estimated arrival time
  - Contact details

> 🔄 _Optional enhancement_: In future versions
