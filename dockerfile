FROM gcc:10.2.0

# Install dependencies for C++ server and Python client
RUN apt-get update && apt-get install -y \
    cmake \
    build-essential \
    python3 \
    python3-pip \
    wget \
    libboost-system-dev \
    libboost-thread-dev \
    tmux \
    && rm -rf /var/lib/apt/lists/*

# Remove any existing old versions of CMake
RUN rm -rf /usr/local/bin/cmake

# Install specific version of CMake (v3.25.3)
RUN wget https://github.com/Kitware/CMake/releases/download/v3.25.3/cmake-3.25.3-linux-x86_64.sh && \
    chmod +x cmake-3.25.3-linux-x86_64.sh && \
    ./cmake-3.25.3-linux-x86_64.sh --skip-license --prefix=/usr/local && \
    rm cmake-3.25.3-linux-x86_64.sh

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Create a build directory for C++ server and build it
RUN mkdir -p build && cd build && cmake .. && make

# Install required Python packages
RUN pip3 install requests

# Expose the port for the server (you can change the port as needed)
EXPOSE 8080

# Set the default command to launch tmux, and split it into two panes for server and client
CMD ["tmux", "new-session", "-d", "bash -c './build/server' && tmux split-window -h 'bash -c \"python3 ./src/Client.py 0.0.0.0 8080\"' && tmux -2 attach-session -d"]
