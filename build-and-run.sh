#!/bin/bash

# Define service paths
ACCOUNT_DETAILS_SERVICE="/home/user/Account-Information-Service/microservices/account-details-service"
ACCOUNT_ADDRESS_SERVICE="/home/user/Account-Information-Service/microservices/account-address-service"
ACCOUNT_BALANCE_SERVICE="/home/user/Account-Information-Service/microservices/account-balance-service"
ACCOUNT_COMPOSITE_SERVICE="/home/user/Account-Information-Service/microservices/ais-composite-service"

# Function to build a service
build_service() {
  local service_dir=$1
  echo "Building service in directory: $service_dir"
  cd "$service_dir" || exit 1
  ./gradlew clean bootJar
  if [ $? -ne 0 ]; then
    echo "Build failed for $service_dir. Exiting."
    exit 1
  fi
  echo "Build successful for $service_dir"
}

# Build each microservice
build_service "$ACCOUNT_DETAILS_SERVICE"
build_service "$ACCOUNT_ADDRESS_SERVICE"
build_service "$ACCOUNT_BALANCE_SERVICE"
#build_service "$ACCOUNT_COMPOSITE_SERVICE"

# Navigate to the root directory
cd /home/user/Account-Information-Service || exit 1

# Build Docker images
echo "Building Docker images..."
docker-compose build
if [ $? -ne 0 ]; then
  echo "Docker build failed. Exiting."
  exit 1
fi
echo "Docker images built successfully."

# Start the application
echo "Starting the application..."
docker-compose up -d
if [ $? -ne 0 ]; then
  echo "Failed to start the application. Exiting."
  exit 1
fi
echo "Application started successfully."
