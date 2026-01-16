# ParkingLot Management System

## Features

- **Vehicle Management**: Track vehicles entering and exiting the parking lot
- **Ticket Generation**: Automated ticket creation with validation
- **Gate Management**: Entry and exit gate control
- **Multiple Vehicle Types**: Support for CAR, BIKE, TRUCK, and more
- **Exception Handling**: Robust error handling for invalid operations
- **Unit Testing**: Comprehensive test coverage with JUnit 5
- **CI/CD Pipeline**: 11-stage automated pipeline
- **Security**: Integrated security scanning at every stage
- **Containerization**: Docker multi-stage builds
- **Kubernetes**: Scalable deployment with K8s

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 20 |
| **Build Tool** | Apache Maven | 3.9+ |
| **Testing** | JUnit 5 | 5.10.1 |
| **Code Quality** | Checkstyle | 10.12.5 |
| **Containerization** | Docker | Latest |
| **Orchestration** | Kubernetes | v1.28+ |
| **CI/CD** | GitHub Actions | Latest |
| **Security** | CodeQL, Trivy | Latest |

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/ParkingLot.git
cd ParkingLot
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

### 4. Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.parking.Main"
```

Expected output: `Hello world!`

## 🔐 Secrets Configuration

GitHub Secrets are required for DockerHub integration. Follow these steps:

### Step 1: Create DockerHub Access Token

1. Log in to DockerHub
2. Go to **Account Settings** → **Security** → **New Access Token**
3. Give it a name: `github-actions-parkinglot`
4. Set permissions: **Read & Write**
5. Click **Generate**
6. **Copy the token immediately** (you won't see it again!)

### Step 2: Configure GitHub Secrets

1. Go to your GitHub repository
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**

#### Add DOCKERHUB_USERNAME:

- **Name**: `DOCKERHUB_USERNAME`
- **Value**: Your DockerHub username
- Click **Add secret**

#### Add DOCKERHUB_TOKEN:

- **Name**: `DOCKERHUB_TOKEN`
- **Value**: The access token you created in Step 2
- Click **Add secret**

### Step 3: Update CI Workflow

The workflow file (`.github/workflows/ci.yml`) already references these secrets:
- `${{ secrets.DOCKERHUB_USERNAME }}`
- `${{ secrets.DOCKERHUB_TOKEN }}`

No changes needed if secrets are named correctly!

### Step 4: Verify Secrets

1. Push a commit to trigger the CI pipeline
2. Check the **"Login to DockerHub"** and **"Push to Registry"** steps
3. They should complete successfully

### Secret Names Summary

| Secret Name | Description | Example |
|------------|-------------|---------|
| `DOCKERHUB_USERNAME` | Your DockerHub username | `johndoe` |
| `DOCKERHUB_TOKEN` | DockerHub access token | `dckr_pat_xxxxx...` |


## 🔄 CI/CD Pipeline Explanation

### Pipeline Overview

The CI/CD pipeline consists of **11 automated stages** that run on every push and pull request:

```
┌─────────────────────────────────────────────────────────┐
│              CI/CD Pipeline Stages                       │
├─────────────────────────────────────────────────────────┤
│  1. Checkout Source Code                                 │
│  2. Setup Java 20 + Maven                                │
│  3. Code Quality Check (Checkstyle)                      │
│  4. Run Unit Tests                                       │
│  5. Build Application                                     │
│  6. Security - SAST (CodeQL)                             │
│  7. Security - SCA (Trivy Filesystem)                    │
│  8. Docker Build                                         │
│  9. Container Security Scan (Trivy Image)                │
│ 10. Container Runtime Test                               │
│ 11. Push to DockerHub                                    │
└─────────────────────────────────────────────────────────┘
```

### Stage-by-Stage Breakdown

#### Stage 1: Checkout Source Code
- **Action**: `actions/checkout@v4`
- **Purpose**: Retrieves source code from repository
- **Duration**: ~5 seconds

#### Stage 2: Setup Java 20
- **Action**: `actions/setup-java@v4`
- **Purpose**: Installs JDK 20 (Temurin distribution)
- **Features**: Maven dependency caching for faster builds
- **Duration**: ~10 seconds (first run), ~2 seconds (cached)

#### Stage 3: Code Quality Check (Checkstyle)
- **Command**: `mvn validate`
- **Purpose**: Enforces coding standards
- **Rules**: 50+ Checkstyle rules (naming, imports, whitespace, etc.)
- **Failure**: Pipeline stops if violations found
- **Duration**: ~15 seconds

#### Stage 4: Run Unit Tests
- **Command**: `mvn test`
- **Purpose**: Executes all unit tests
- **Framework**: JUnit 5
- **Tests**: 11 unit tests (Main, Vehicle, TicketService)
- **Failure**: Pipeline stops if tests fail
- **Duration**: ~20 seconds

#### Stage 5: Build Application
- **Command**: `mvn clean package -DskipTests`
- **Purpose**: Compiles and packages application
- **Output**: JAR file in `target/` directory
- **Duration**: ~30 seconds

#### Stage 6: Security - SAST (CodeQL)
- **Action**: `github/codeql-action/analyze@v3`
- **Purpose**: Static Application Security Testing
- **Analysis**: Java code security vulnerabilities
- **Output**: Results in GitHub Security tab
- **Duration**: ~2 minutes

#### Stage 7: Security - SCA (Trivy Filesystem)
- **Action**: `aquasecurity/trivy-action@0.20.0`
- **Purpose**: Software Composition Analysis
- **Scan**: Maven dependencies for CVEs
- **Severity**: CRITICAL, HIGH vulnerabilities
- **Output**: SARIF report uploaded to GitHub Security
- **Duration**: ~30 seconds

#### Stage 8: Docker Build
- **Command**: `docker build`
- **Purpose**: Creates container image
- **Type**: Multi-stage build (builder + runtime)
- **Features**: Non-root user, optimized size
- **Duration**: ~3 minutes

#### Stage 9: Container Security Scan
- **Action**: `aquasecurity/trivy-action@0.20.0`
- **Purpose**: Scans Docker image for vulnerabilities
- **Scope**: Base image and installed packages
- **Output**: SARIF report
- **Duration**: ~20 seconds

#### Stage 10: Container Runtime Test
- **Command**: `docker run`
- **Purpose**: Verifies container runs correctly
- **Test**: Checks application output
- **Duration**: ~10 seconds

#### Stage 11: Push to DockerHub
- **Commands**: `docker login` + `docker push`
- **Purpose**: Publishes image to registry
- **Authentication**: Uses GitHub Secrets
- **Tagging**: Image tagged with commit SHA
- **Duration**: ~1 minute

## 💻 Local Development

### Build the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Build JAR
mvn package

# Run application
java -jar target/parking-lot-app.jar
```

### Code Quality Checks

```bash
# Run Checkstyle
mvn validate

# Fix Checkstyle violations
mvn checkstyle:check
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=VehicleTest

# Run with coverage (if configured)
mvn test jacoco:report
```

## ☸️ Kubernetes Deployment

### Prerequisites

- Minikube installed and running
- kubectl configured
- Docker running

### Step 1: Start Minikube

```bash
# Start Minikube cluster
minikube start --driver=docker

# Verify cluster is running
kubectl cluster-info
```

### Step 2: Configure Docker for Minikube

```bash
# Point Docker to Minikube's Docker daemon
eval $(minikube docker-env)

# Verify
docker ps
```

### Step 3: Build Docker Image

```bash
# Build image (using Minikube's Docker)
docker build -t parking-lot-app:latest .
```

### Step 4: Update Deployment File

Edit `k8s/deployment.yaml` and ensure:
```yaml
image: parking-lot-app:latest
imagePullPolicy: Never  # Use local image
```

### Step 5: Deploy to Kubernetes

```bash
# Create namespace
kubectl apply -f k8s/namespace.yaml

# Create ConfigMap
kubectl apply -f k8s/configmap.yaml

# Deploy application
kubectl apply -f k8s/deployment.yaml

# Create service
kubectl apply -f k8s/service.yaml
```

### Step 6: Verify Deployment

```bash
# Check all resources
kubectl get all -n parking-lot

# Check pods
kubectl get pods -n parking-lot

# View logs
kubectl logs -l app=parking-lot -n parking-lot

# Access service
minikube service parking-lot-service -n parking-lot
```

### Step 7: Cleanup

```bash
# Delete all resources
kubectl delete namespace parking-lot

# Stop Minikube
minikube stop

# Delete cluster (optional)
minikube delete
```

