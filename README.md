# Product Catalog API

A REST API for a store product catalog, built with Java and Spring Boot. It manages products, categories, product images, and users, with JWT authentication and MySQL persistence.

## Features

- Create, list, update, enable, and disable products and categories.
- Track product GTINs, descriptions, prices, stock, and category assignments.
- Upload and retrieve product images as Base64, with files stored locally.
- Register users and authenticate with JWT bearer tokens.
- Hash passwords with BCrypt and restrict user lookup to administrators.
- Explore the API through Swagger UI.

## Technology

- Java 21
- Spring Boot 4.0.2
- Spring Web MVC, Data JPA, Security, and Validation
- MySQL and Hibernate
- JJWT 0.11.5
- springdoc OpenAPI 2.8.6
- Maven Wrapper

Versions above reflect the current `pom.xml`.

## Local setup

You need JDK 21, a running MySQL server, and internet access for Maven's initial dependency download. A separate Maven installation is unnecessary.

Run the following commands from the repository root. On Windows, use `mvnw.cmd` instead of `./mvnw` and set environment variables using your shell's syntax.

### 1. Create a database

Connect to MySQL with an account that can create databases and run:

```sql
CREATE DATABASE dwb2026_2 CHARACTER SET utf8mb4;
```

The repository does not include schema migrations or a SQL setup script. For a new local development database, enable Hibernate schema updates as shown below. This creates tables from the entity mappings; it does not reproduce any additional constraints from an externally maintained schema.

### 2. Configure the application

Override the connection settings in `src/main/resources/application.properties` using environment variables:

```sh
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/dwb2026_2'
export SPRING_DATASOURCE_USERNAME='your_mysql_user'
export SPRING_DATASOURCE_PASSWORD='your_mysql_password'
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

Use `update` for local development only. If you already have a provisioned schema, omit that override and use your existing database configuration. The checked-in properties do not enable automatic schema creation.

| Setting | Default | Purpose |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/dwb2026_2` | Database connection |
| `SPRING_DATASOURCE_USERNAME` | `root` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Defined in `application.properties` | Database password |
| `SERVER_PORT` | `8080` | HTTP port |
| `APP_UPLOAD_DIR` | `uploads` | Upload root, relative to the working directory |
| `APP_UPLOAD_IMAGES` | `img` | Image subdirectory |

### 3. Start the server

```sh
./mvnw spring-boot:run
```

With the default port, the base URL is `http://localhost:8080`.

- Swagger UI: <http://localhost:8080/swagger-ui/index.html>
- OpenAPI JSON: <http://localhost:8080/v3/api-docs>

## Authentication and examples

Register a user:

```sh
curl -X POST http://localhost:8080/user/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","email":"demo@example.com","password":"Example123!"}'
```

Passwords must contain at least eight characters, an uppercase letter, a digit, and one of `@_$#!%*?&`.

Log in:

```sh
curl -X POST http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"Example123!"}'
```

The response has the form `{"token":"..."}`. Tokens expire after one hour. Copy the token into a shell variable:

```sh
export TOKEN='paste-token-here'
```

Create a category, then list categories to find its generated `category_id`:

```sh
curl -X POST http://localhost:8080/category \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"category":"Books","tag":"books"}'

curl http://localhost:8080/category \
  -H "Authorization: Bearer $TOKEN"
```

Create a product using an existing category ID (replace `1` below):

```sh
curl -X POST http://localhost:8080/product \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"gtin":"9780134685991","product":"Java Book","description":"A programming reference","price":49.99,"stock":10,"category_id":1}'
```

## API endpoints

All routes below require a bearer token except registration and login. Swagger UI and OpenAPI documentation are also public. `GET /user/{id}` additionally requires the exact authority `Administrator`.

| Method | Path | Operation |
| --- | --- | --- |
| POST | `/user/register` | Register a user (public) |
| POST | `/login` | Obtain a JWT (public) |
| GET | `/product` | List products |
| GET | `/product/{id}` | Get product details |
| POST | `/product` | Create a product |
| PUT | `/product/{id}` | Update a product |
| PATCH | `/product/{id}/enable` | Enable a product |
| PATCH | `/product/{id}/disable` | Disable a product |
| GET | `/category` | List categories |
| GET | `/category/active` | List active categories |
| POST | `/category` | Create a category |
| PUT | `/category/{id}` | Update a category |
| PATCH | `/category/{id}/enable` | Enable a category |
| PATCH | `/category/{id}/disable` | Disable a category |
| GET | `/product/{id}/image` | List product images as Base64 |
| POST | `/product/{id}/image` | Upload a product image |
| DELETE | `/product/{id}/image/{product_image_id}` | Delete an image and its file |
| GET | `/user/{id}` | Get user details (administrator) |
| PUT | `/user/{id}` | Update username, email, and password |
| PATCH | `/user/{id}/enable` | Enable a user |
| PATCH | `/user/{id}/disable` | Disable a user |

Registration does not assign roles. Administrator access requires an `Administrator` role entry for the user in `user_roles`; no role-management endpoint is implemented. User update and status routes currently require authentication only.

### Product images

Image uploads accept JSON with `product_id` and `image`, where `image` is raw Base64 without a `data:image/png;base64,` prefix:

```json
{
  "product_id": 1,
  "image": "<base64-encoded PNG bytes>"
}
```

Send this body to `POST /product/1/image`. Keep the URL ID and `product_id` equal: the current upload handler uses the body ID and does not compare it with the URL ID.

Files are saved under `uploads/img/product/` with generated `.png` filenames. The service writes the decoded bytes without converting their format. Image-list responses contain Base64 data; a missing file produces an empty image string. The upload directory is ignored by Git.

## Build and test

With the database environment configured:

```sh
./mvnw test
./mvnw clean package
java -jar target/product-0.0.1-SNAPSHOT.jar
```

The existing test is a Spring application-context smoke test and requires a working database connection. `package` also runs tests by default.

## Project structure

```text
src/main/java/com/product/
├── api/
│   ├── controller/     HTTP endpoints
│   ├── dto/            Request and response models
│   ├── entity/         JPA entities
│   ├── repository/     Database access
│   └── service/        Business logic and image storage
├── common/mapper/      Product response mapping
├── config/             JWT, security, and OpenAPI configuration
└── exception/          API exception handling
src/main/resources/application.properties
src/test/java/com/product/ProductApplicationTests.java
```
