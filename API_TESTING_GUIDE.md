# StackCoders Book Store — API Testing Guide

Base URL (local): `http://localhost:8080`

---

## 1. POST /auth/register

**Public** — no token required.

**Request**
```http
POST /auth/register
Content-Type: application/json

{
    "username": "johndoe",
    "email": "john@example.com",
    "password": "SecurePass123"
}
```

**Success response — 201 Created**
```json
{
    "success": true,
    "message": "Registration successful"
}
```

**Failure — 409 Conflict (duplicate email)**
```json
{
    "success": false,
    "message": "An account with this email already exists"
}
```

**Failure — 400 Bad Request (validation)**
```json
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        "email: Email must be a valid email address",
        "password: Password must be at least 6 characters"
    ]
}
```

---

## 2. POST /auth/login

**Public** — no token required.

**Request**
```http
POST /auth/login
Content-Type: application/json

{
    "email": "john@example.com",
    "password": "SecurePass123"
}
```

**Success response — 200 OK**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "user": {
            "id": 1,
            "username": "johndoe",
            "email": "john@example.com",
            "role": "USER"
        }
    }
}
```

**Failure — 401 Unauthorized**
```json
{
    "success": false,
    "message": "Invalid email or password"
}
```

---

## 3. POST /auth/logout

**Protected** — requires `Authorization: Bearer <token>`.

**Request**
```http
POST /auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Success response — 200 OK**
```json
{
    "success": true,
    "message": "Logout successful"
}
```

**Failure — 401 Unauthorized (token not found / already revoked)**
```json
{
    "success": false,
    "message": "Token not recognized"
}
```

---

## 4. POST /auth/refresh

**Protected** — requires `Authorization: Bearer <token>`.

**Request**
```http
POST /auth/refresh
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Success response — 200 OK**
```json
{
    "success": true,
    "message": "Token refreshed successfully",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.NEW_TOKEN...",
        "user": {
            "id": 1,
            "username": "johndoe",
            "email": "john@example.com",
            "role": "USER"
        }
    }
}
```

**Failure — 401 Unauthorized**
```json
{
    "success": false,
    "message": "Token has already been revoked"
}
```

---

## 5. GET /auth/me

**Protected** — requires `Authorization: Bearer <token>`.

**Request**
```http
GET /auth/me
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Success response — 200 OK**
```json
{
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "role": "USER",
    "createdAt": "2026-07-29T10:15:30"
}
```

**Failure — 401 Unauthorized (missing/invalid/expired token)**
```json
{
    "success": false,
    "message": "Authentication required to access this resource"
}
```

---

## Expected HTTP Status Code Summary

| Scenario | Status |
|---|---|
| Register success | 201 |
| Login success | 200 |
| Logout success | 200 |
| Refresh success | 200 |
| Get current user success | 200 |
| Validation error (bad request body) | 400 |
| Missing/invalid/expired/revoked token | 401 |
| Authenticated but forbidden resource | 403 |
| Resource not found | 404 |
| Duplicate email on register | 409 |
| Unexpected server error | 500 |

---

## Postman Collection Setup

1. Create an environment with variable `base_url = http://localhost:8080`.
2. Create a collection variable `access_token` (leave empty initially).
3. In the **Login** request's **Tests** tab, add:
   ```javascript
   const res = pm.response.json();
   if (res.success) {
       pm.collectionVariables.set("access_token", res.data.token);
   }
   ```
4. For every protected request (`logout`, `refresh`, `me`), set the header:
   ```
   Authorization: Bearer {{access_token}}
   ```
5. Suggested run order for a full manual test pass:
   `register` → `login` → `me` → `refresh` → `me` (with new token) → `logout` → `me` (should now 401).

---

## Manual cURL Smoke Test

```bash
# 1. Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","email":"john@example.com","password":"SecurePass123"}'

# 2. Login (capture the token from the response)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"SecurePass123"}'

# 3. Current user (replace TOKEN)
curl http://localhost:8080/auth/me -H "Authorization: Bearer TOKEN"

# 4. Refresh (replace TOKEN)
curl -X POST http://localhost:8080/auth/refresh -H "Authorization: Bearer TOKEN"

# 5. Logout (replace TOKEN)
curl -X POST http://localhost:8080/auth/logout -H "Authorization: Bearer TOKEN"
```
