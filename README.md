# Todo App - Dockerized Setup

## Overview
This project is a full-stack **Todo App** that consists of:
- **Frontend:** Angular application running on **port 4200**
- **Backend:** Spring Boot application running on **port 8080**
- **Database:** PostgreSQL running on **port 5433** (inside the container it's 5432)

The entire project is **dockerized** using `docker-compose`.

---

## Prerequisites
Before you start, ensure you have installed:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- (Optional) Node.js & Angular CLI if you want to run the frontend manually

---

## Project Structure
```
project-root/
├── frontend/          # Angular app
│   ├── src/
│   ├── package.json
│   ├── angular.json
│   ├── Dockerfile
│   └── ...
│
├── backend/           # Spring Boot app
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── ...
│
├── docker-compose.yml # Docker Compose file
├── .env               # Environment variables
└── README.md          # Documentation
```

---

## Environment Variables
Create a `.env` file in the **same directory as `docker-compose.yml`** with the following content:
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=Ayesh@14775
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/todo
SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
```
change the POSTGRES_USER and POSTGRES_PASSWORD according to your setup.
---

## Running the Project
### 1️⃣ **Run the Full Application with Docker**
Execute the following command in the project root:
```sh
docker compose up -d
```

### 2️⃣ **Run Backend Without Docker (For Development)**
```sh
cd backend
export $(cat ../.env | xargs) && mvn spring-boot:run
```

### 3️⃣ **Run Frontend Without Docker (For Development)**
```sh
cd frontend
npm install
ng serve --host 0.0.0.0 --port 4200
```

---

## Docker Compose Configuration
Your `docker-compose.yml` file includes:
```yaml
version: '3.8'

services:
  angular-app:
    build: ./frontend
    ports:
      - "4200:4200"
    depends_on:
      - spring-boot-app
    container_name: todo-frontend
    restart: unless-stopped

  spring-boot-app:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - postgres_db
    container_name: todo-backend
    environment:
      SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
    restart: unless-stopped

  postgres_db:
    image: postgres:17
    container_name: postgres-container
    ports:
      - "5433:5432"
    environment:
      POSTGRES_DB: todo
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    volumes:
      - postgres-data:/var/lib/postgresql/data
    restart: unless-stopped

volumes:
  postgres-data:

```

---

## Stopping the Application
To stop the running containers, use:
```sh
docker compose down
```

To remove all Docker images and containers, run:
```sh
docker system prune -a
```

---

## Troubleshooting
### 🔴 **Port Conflicts**
If you see errors like:
```
Error response from daemon: Ports are not available: exposing port TCP 0.0.0.0:80 -> 0.0.0.0:0
```
It means **port 80 is already in use**. You can:
1. Stop the service using port 80 (`netstat -ano | findstr :80` in Windows).
2. Change the `ports` mapping in `docker-compose.yml` to an available port.

### 🔴 **Database Connection Issues**
If Spring Boot cannot connect to PostgreSQL:
- Ensure `docker-compose up` is running.
- Check logs with `docker logs todo-backend`.
- Verify `.env` file has correct database credentials.

---

## Contributors
- **Ayesh** *(Project Lead)*

---

## License
This project is **open-source** and free to use!

