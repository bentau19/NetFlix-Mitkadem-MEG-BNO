# Use a base image with CMake and a build environment
FROM ubuntu:20.04

# Install necessary tools and dependencies
RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    g++ \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the CMake commands to configure and build the project
RUN mkdir -p build && cd build && cmake .. && cmake --build .

# Set the default command to run your project binary
CMD ["./build/debug/unitest"]
