# OrderCoffee - Application

An internal order management system where customers place drink orders , and the baristas (using a Java Swing desktop app) can view and update the order status in real-time

## Features

- 🤖 Integration with multiple AI models via Ollama
- 💬 Chat interface with real-time message streaming
- 🧠 "Thinking" indicators showing the AI's reasoning process
- 📚 Chat history management
- 👥 User authentication and account management
- 🔄 Model switching during conversations
- 📱 Responsive design for desktop and mobile

## Tech Stack

### Frontend

- Java Swing
- FlatLaf - UI/UX
- Midlayout - setsize
- Retrofit to consume RESTful APIs
- JTable for displaying orders

### Backend

- Spring Boot 3
- Spring Data JPA
- Mysql for data storage
- SpringMVC for reactive APIs

### Infrastructure

- Docker & Docker Compose

## Prerequisites

- Docker & Docker Compose
- Git

## Quick Start with Docker

The fastest way to get started is using Docker Compose:

```bash
# Clone the repository
git clone https://github.com/yourusername/vivu-chat.git
cd vivu-chat

# Start all services
cd src
docker-compose up -d

# Pull an AI model (after services are running)
docker exec -it vivuchat-ollama ollama pull gemma:2b
```

After the services are running, access:
- Desktop: http://localhost
- API: http://localhost:8080
- Ollama: http://localhost:11434

## Running on Apple Silicon (M1/M2)

For Mac users with Apple Silicon (M1/M2) processors, our Docker configuration supports ARM64 architecture:

```bash
# Clone the repository
git clone https://github.com/yourusername/vivu-chat.git
cd vivu-chat

# Edit docker-compose.yml to use ARM64 architecture
# Change BUILDPLATFORM values from linux/amd64 to linux/arm64

# Start all services
cd src
docker-compose up -d

# Pull an AI model optimized for ARM
docker exec -it vivuchat-ollama ollama pull gemma:2b
```

### Specifying Platform in Docker Compose

There are multiple ways to specify the platform when running Docker Compose:

1. **Using environment variables** (recommended):
```bash
# For ARM64 (Apple M1/M2)
DOCKER_DEFAULT_PLATFORM=linux/arm64 docker-compose up -d

# For AMD64 (Intel/AMD processors)
DOCKER_DEFAULT_PLATFORM=linux/amd64 docker-compose up -d
```

2. **Using Docker Compose build arguments** (already configured):
```bash
# Edit the BUILDPLATFORM args in docker-compose.yml before running
docker-compose up --build
```

3. **Using Docker BuildKit**:
```bash
# Enable BuildKit
export DOCKER_BUILDKIT=1

# Set the platform
export BUILDPLATFORM=linux/arm64

# Run Docker Compose
docker-compose up --build
```

If you experience any architecture-related issues:

- Make sure Docker Desktop is updated to the latest version
- Verify that the BUILDPLATFORM is set to `linux/arm64` in the docker-compose.yml file
- Check that both Dockerfiles properly use the platform variable with `--platform=${BUILDPLATFORM}`

## Detailed Docker Setup

### Environment Configuration

The Docker setup uses environment variables to configure the services. The key variables are already set in the `docker-compose.yml` file, but you can customize them:

```yaml
# PostgreSQL settings
POSTGRES_DB: vivuchat
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres

# Backend settings
SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/vivuchat
OLLAMA_API_BASE_URL: http://ollama:11434
APP_OLLAMA_APIURL: http://ollama:11434/api

# Frontend settings 
VITE_API_BASE_URL: /api
```

### Running Different AI Models

Ollama supports various AI models. To use a different model:

```bash
# List available models
docker exec -it vivuchat-ollama ollama list

# Pull a new model
docker exec -it vivuchat-ollama ollama pull llama3:8b
docker exec -it vivuchat-ollama ollama pull gemma:7b

# For larger models (if you have enough GPU RAM)
docker exec -it vivuchat-ollama ollama pull mixtral:8x7b
```

Once you've pulled models, you can select them in the ViVu Chat UI's model selector.

### Enabling GPU Support

For better performance, enable GPU support by modifying the `docker-compose.yml`:

```yaml
ollama:
  # ...existing settings...
  deploy:
    resources:
      reservations:
        devices:
          - driver: nvidia
            count: 1
            capabilities: [gpu]
```

### Docker Volume Management

Docker volumes store persistent data:

```bash
# List volumes
docker volume ls | grep vivuchat

# Backup PostgreSQL data
docker exec -it vivuchat-postgres pg_dump -U postgres vivuchat > backup.sql

# Clean up (WARNING: Removes all data)
docker-compose down -v
```

## Troubleshooting Docker Setup

### Connection Issues

If the frontend can't connect to the backend:
- Ensure the `VITE_API_BASE_URL` is set correctly in Dockerfile.client
- Check NGINX configuration in `deployment/nginx/default.conf`
- Verify network connectivity: `docker network inspect vivuchat-network`

### Ollama Model Problems

If models aren't loading:
- Check Ollama logs: `docker logs vivuchat-ollama`
- Ensure Ollama has enough resources
- Verify the API URL: `http://ollama:11434`

### Database Connection Issues

If PostgreSQL connection fails:
- Ensure the database is running: `docker ps | grep postgres`
- Check connection settings in `application-docker.properties`
- Verify database initialization: `docker exec -it vivuchat-postgres psql -U postgres -c '\l'`

### Architecture-specific Issues

If encountering "exec format error" messages:
- Verify your architecture: `uname -m`
- Set the platform explicitly: `DOCKER_DEFAULT_PLATFORM=linux/arm64 docker-compose up -d`
- Update BUILDPLATFORM in docker-compose.yml accordingly
- Ensure base images support your architecture
- Try using multiarch images like `--platform=linux/arm64` for Apple Silicon

## Development Setup

### Frontend Development (Local)

```bash
cd src/client

# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build
```

### Backend Development (Local)

```bash
cd src/server

# Run using Maven
./mvnw spring-boot:run

# Package as JAR
./mvnw package

# Run with specific profile
java -jar target/*.jar --spring.profiles.active=dev
```

## Environment Variables

### Frontend Environment

Frontend environment variables are set during build:
## Contributors

<!-- readme: contributors -start -->
<table>
	<tbody>
		<tr>
            <td align="center">
                <a href="https://github.com/HungPig">
                    <img src="https://avatars.githubusercontent.com/u/118031742?v=4" width="100;" alt="Liquidwe"/>
                    <br />
                    <sub><b>Hưng Heo</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/VanKhoa2301">
                    <img src="https://avatars.githubusercontent.com/u/199430332?v=4" width="100;" alt="fishTsai20"/>
                    <br />
                    <sub><b>Khoa Cao Van</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/duc-tuan0207">
                    <img src="https://avatars.githubusercontent.com/u/154722842?v=4" width="100;" alt="lxcong"/>
                    <br />
                    <sub><b>Duc Tuan</b></sub>
                </a>
            </td>
	<tbody>
</table>
<!-- readme: contributors -end -->
## Document

Not yet

## Library Resources

- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) - FlatLaf library for the modern UI design theme
- [MigLayout](https://github.com/mikaelgrev/miglayout) - MigLayout library for flexible layout management
