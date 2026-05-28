# Secure Mini Password Manager

A refactored and optimized Java-based Password Manager project developed using Data Structures & Algorithms concepts, secure password hashing, and clean code principles.

This project was enhanced from a simple open-source Java Password Manager and redesigned with:

* better readability
* improved modularity
* optimized searching
* stronger validation
* enhanced console UI
* additional security features
* improved maintainability

---

# Project Objective

The objective of this project is to securely manage user credentials while demonstrating:

* Code Refactoring
* Performance Optimization
* Clean Code Practices
* Data Structure Usage
* Time Complexity Improvements
* Security Enhancements

This project was developed as part of:

**CODTECH Internship Task-4 — Code Refactoring & Performance Optimization**

---

# Features

## User Features

* User Registration
* Secure User Login
* Two-Factor Authentication (2FA)
* Add Credentials
* View Stored Credentials
* Search Credentials
* Delete Credentials
* Credential Statistics
* Strong Password Generator
* Password Strength Checker

---

# Data Structures Used

| Data Structure | Usage                                 |
| -------------- | ------------------------------------- |
| HashMap        | Fast credential storage and searching |
| Nested HashMap | User-wise credential management       |
| StringBuilder  | Efficient password generation         |

---

# DSA & Performance Optimization

## Fast Credential Searching

The project uses:

```java
HashMap<String, Credential>
```

instead of inefficient linear searching.

### Time Complexity

| Operation         | Complexity |
| ----------------- | ---------- |
| Add Credential    | O(1)       |
| Search Credential | O(1)       |
| Delete Credential | O(1)       |
| Login User Lookup | O(1)       |

This significantly improves performance for larger datasets.

---

# Security Improvements

## Implemented Security Features

* SHA-256 Password Hashing
* Salt Generation using SecureRandom
* Simulated Two-Factor Authentication
* Hidden Password Display
* Strong Password Validation

---

# Refactoring Improvements

## Original Code Problems

The original project had:

* poor readability
* weak variable naming
* limited features
* basic console interface
* no password strength validation
* no credential searching
* no credential deletion
* weak modularity
* mixed responsibilities in methods

---

# Enhancements Made

## Code Quality Improvements

* Improved variable names
* Added modular methods
* Reduced duplicate logic
* Added section-based code organization
* Improved console formatting
* Added validation handling

---

## Feature Enhancements

### Added:

* Password Generator
* Credential Search
* Credential Deletion
* Credential Statistics
* Password Strength Meter
* Better Console UI
---

# Technologies Used

* Java
* HashMap
* SecureRandom
* SHA-256
* Base64 Encoding
* Object-Oriented Programming

---

# Working Samples 

<img width="1365" height="720" alt="image" src="https://github.com/user-attachments/assets/da50f5c3-23ee-4ca7-8a1c-f351e5be215c" />

<img width="1365" height="718" alt="image" src="https://github.com/user-attachments/assets/5eafe74d-ab47-4830-9224-4289c3e85d5d" />

---

# How To Run

## 1. Clone Repository

```bash
git clone <repository-link>
```

## 2. Open Project

Open in:

* IntelliJ IDEA
* VS Code

## 3. Run

Run:

```text
MiniPasswordManager.java
```

---

# Learning Outcomes

Through this project, the following concepts were learned and implemented:

* Code Refactoring
* Performance Optimization
* Data Structures & Algorithms
* HashMap Optimization
* Secure Password Hashing
* Java OOP Design
* Console Application Development
* Clean Code Principles

---

# Future Improvements

Possible future enhancements:

* File Storage
* Database Integration
* GUI Interface
* AES Encryption
* Real OTP System
* Cloud Backup

---
Refactored by:
~Y. Sai Charan Reddy~
