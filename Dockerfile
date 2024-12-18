FROM gcc:10.2.0 AS builder

# Install dependencies for building
RUN apt-get update && apt-get install -y \
    cmake \
    build-essential \
    wget \
    libboost-system-dev \
    libboost-thread-dev \
    && rm -rf /var/lib/apt/lists/*

# Install a specific version of CMake (3.25.3)
RUN wget https://github.com/Kitware/CMake/releases/download/v3.25.3/cmake-3.25.3-linux-x86_64.sh && \
    chmod +x cmake-3.25.3-linux-x86_64.sh && \
    ./cmake-3.25.3-linux-x86_64.sh --skip-license --prefix=/usr/local && \
    rm cmake-3.25.3-linux-x86_64.sh

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN mkdir -p build && cd build && cmake .. && make

# Runtime Stage
FROM python:3.7-slim

# Install runtime dependencies
RUN apt-get update && apt-get install -y \
    libboost-system-dev \
    libboost-thread-dev \
    tmux \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy build artifacts
COPY --from=builder /app/build /app/build
COPY --from=builder /app/src /app/src

# Install Python dependencies
RUN pip3 install requests

# Expose the server port
EXPOSE 8080

# Run server and client in tmux
CMD ["bash", "-c", "./build/server"]
