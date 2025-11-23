<h1 align="center">iVote – Electronic Voting System</h1>

<p align="center">
  <b>A secure, role-based digital voting platform built using Java & MySQL</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Database-MySQL-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Architecture-MVC-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Version-1.0-purple?style=for-the-badge" />
</p>

---

## 📌 Overview

**iVote** is a complete **Electronic Voting System (EVS)** developed using **Core Java, JDBC, and MySQL**.  
It automates the entire election workflow from user registration to secure vote casting, ensuring transparency and enforcing **one-person-one-vote**.

The system implements **three fully role-based modules**:

- **Admin**
- **Electoral Officer**
- **Voter (User)**

This project follows a full detailed design specification, including use-cases, class diagrams, sequence diagrams, and database schema.

---

## 🚀 Features

### 👨‍💼 Admin
- Create/manage elections  
- Add political parties  
- Add candidates  
- Forward Voter-ID applications  
- Approve election results  
- View pending applications  

### 🧑‍✈️ Electoral Officer
- View forwarded voter-ID requests  
- Approve / reject applications  
- Generate unique voter-ID  
- Manage constituency-based operations  

### 🧑‍⚖️ Voter
- Register an account  
- Apply for Voter-ID  
- Track approval status  
- Cast vote (only once per election)  
- View candidates for elections  
- View election results  

---

## 🛠 Tech Stack

| Layer | Technology |
|------|------------|
| **Language** | Java (Core Java, OOP) |
| **Database** | MySQL |
| **Connectivity** | JDBC |
| **Architecture** | MVC |
| **Tools** | Eclipse ,TableOne |

---

## 🏗 Architecture
Java Application (UI / Console)
|
|-- JDBC (Prepared Statements)
|
MySQL Database (9 normalised tables)

---

## 📁 Project Structure
src/
├── com.ivote.bean
├── com.ivote.dao
├── com.ivote.service
├── com.ivote.util
├── com.ivote.ui (if Swing UI implemented)
└── Main.java


---

## 🗄 Database Schema

### Core Tables:
- `EVS_TBL_User_Credentials`
- `EVS_TBL_User_Profile`
- `EVS_TBL_Election`
- `EVS_TBL_Party`
- `EVS_TBL_Candidate`
- `EVS_TBL_Application`
- `EVS_TBL_Result`
- `EVS_TBL_EO`
- `EVS_TBL_Voter_Details`

### Auto-generated IDs  


---

## 📥 Installation & Setup

### 1️⃣ Clone Repository
```bash
git clone https://github.com/your-username/iVote.git
cd iVote


🔐 Security Features

PreparedStatements → Prevent SQL Injection
Encrypted login workflow
Role-based access control
Login status tracking
Unique auto-generated IDs
Duplicate vote prevention
