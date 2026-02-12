# Auth Service

Auth Service là microservice chịu trách nhiệm **xác thực & cấp phát token (JWT)** cho hệ thống.  
Service này hoạt động như **authentication core**, kết hợp với **Keycloak**, **User Service** và **Spring Security**.

---

## Tech Stack

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Security**
- **JWT**
- **Keycloak**
- **gRPC**
- **Swagger / OpenAPI**
- **Maven**

---

## Chức năng chính

- Đăng nhập (Login)
- Xác thực token JWT
- Làm việc với Keycloak (validate / extract user info)
- Giao tiếp với User Service qua gRPC
- Cung cấp API xác thực cho các service khác

---

## Cấu trúc thư mục
```
src/main/java/com/huytpq/auth_service
├── config # Security, Keycloak config
├── controller # REST API
├── dto # Request / Response DTO
├── grpc # gRPC client
├── security # JWT filter, security config
├── service # Business logic
└── util # JWT utils
```

---

## Luồng xác thực (High-level)

1. Client gửi request login
2. Auth Service:
   - Validate thông tin đăng nhập
   - Gọi User Service qua gRPC để kiểm tra user
   - Tạo JWT token
3. Trả JWT cho client
4. Các service khác validate token thông qua Auth Service / JWT

---

## Yêu cầu môi trường

- Java 17+
- Maven 3.8+
- Keycloak
- User Service (để xác thực user)
- gRPC port mở giữa các service

---

## Cấu hình

Ví dụ `application.yml`:

```yml
server:
  port: 8081

jwt:
  secret: your-secret-key
  expiration: 3600000

keycloak:
  realm: your-realm
  auth-server-url: http://localhost:8080
  resource: auth-service
  credentials:
    secret: your-client-secret
```
## Chạy project
- Chạy bằng Maven
```
./mvnw spring-boot:run
```
hoặc
```
mvn clean install
java -jar target/auth-service-0.0.1-SNAPSHOT.jar
```
## REST API
- Base URL:
```
/api/auth
```
## Các endpoint chính
```
Method	  Endpoint	            Mô tả
POST	  /api/auth/login       Đăng nhập
POST	  /api/auth/validate	Validate JWT
GET	      /api/auth/me	        Lấy thông tin user từ token
  ```
## Swagger
- Truy cập Swagger UI:
```
http://localhost:8081/swagger-ui.html
```
## gRPC
- Auth Service sử dụng gRPC client để gọi User Service nhằm:

    Kiểm tra user tồn tại.

    Lấy thông tin user nội bộ.

## Test
```
mvn test
```
