# Wallet API

A Spring Boot-based digital wallet application that provides RESTful endpoints for managing wallets, performing deposits and withdrawals, and approving transactions with role-based access control.

---

## Technologies

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA (Hibernate)
- Spring Security with JWT
- Maven
- H2 (in-memory, for development)
- Swagger / OpenAPI


## How to Open the Project in Eclipse

1. Open Eclipse.
2. Go to `File > Import > Maven > Existing Maven Projects`.
3. Select the root folder of this project (`wallet-api`).
4. Click `Finish`.
5. Right-click the project → `Run As > Spring Boot App`.

## Swagger

http://localhost:8083/swagger-ui/index.html

### Roles:
This application uses **JWT (JSON Web Token)** for securing endpoints.
Authorization: Bearer <token>

- `EMPLOYEE`: Can create and manage their own wallets, can approve transactions and view all wallets.
- `CUSTOMER`: Can create and manage their own wallets.

POST /api/auth/login
Content-Type: application/json

users:
oktay.memmedli EMPLOYEE
ali.yilmaz CUSTOMER

{
  "username": "oktay.memmedli",
  "password": "123456"
}

{
  "username": "ali.yilmaz",
  "password": "123456"
}

## 💾 Database

- Uses **H2 in-memory** database for development.
http://localhost:8083/h2-console

| Property         | Value                        |
|------------------|------------------------------|
| **JDBC URL**     | `jdbc:h2:mem:walletdb`       |
| **Username**     | `sa`                         |
| **Password**     | *(leave blank)*              |
| **Driver Class** | `org.h2.Driver`              |


data.sql
-- 1. Kullanıcı ekle
INSERT INTO users ( name, surname, tckn, role, username, password)
VALUES 
('Ali', 'Yılmaz', '12345678901', 'CUSTOMER', 'ali.yilmaz', '$2a$10$/rSzRVZ21bHaCJj55vAKBu540LizlpJn6z7oTRrP3hfClUOtySbhO'), -- şifre: 1234
( 'Oktay', 'Memmedli', '23456789012', 'EMPLOYEE', 'oktay.memmedli', '$2a$10$/rSzRVZ21bHaCJj55vAKBu540LizlpJn6z7oTRrP3hfClUOtySbhO'); -- şifre: 1234

-- 2. Cüzdan ekle
INSERT INTO wallet ( customer_id, wallet_name, currency, active_for_shopping, active_for_withdraw, balance, usable_balance)
VALUES 
( 1, 'Ali1', 'TRY', true, true, 1000.00, 1000.00);

-- 3. Örnek işlem (deposit)
INSERT INTO transaction ( wallet_id, amount, type, opposite_party_type, opposite_party, status)
VALUES
( 1, 1000.00, 'DEPOSIT', 'IBAN', 'TR0001000200030004', 'APPROVED');


## 📦 API Endpoints

| Method | Endpoint                             | Description                    |
|--------|--------------------------------------|--------------------------------|
| POST   | `/api/wallets`                       | Create a new wallet            | 
| GET    | `/api/wallets`                       | List all wallets               | 
| POST   | `/api/wallets/{id}/deposit`          | Deposit money into a wallet    |
| POST   | `/api/wallets/{id}/withdraw`         | Withdraw money from a wallet   | 
| PATCH  | `/api/transactions/{id}/approve`     | Approve a transaction          |
| PATCH  | `/api/transactions/{id}/deny`        | Deny a transaction             |
| POST   | `/api/auth/login`                    | User login and JWT retrieval   | 
