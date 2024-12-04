FROM gcc:10.2.0
# Install CMake and any other required tools
RUN ls -R
RUN apt-get update && apt-get install -y cmake build-essential wget

# Install specific version of CMake (if needed)
RUN wget https://github.com/Kitware/CMake/releases/download/v3.25.3/cmake-3.25.3-linux-x86_64.sh && \
    chmod +x cmake-3.25.3-linux-x86_64.sh && \
    ./cmake-3.25.3-linux-x86_64.sh --skip-license --prefix=/usr/local && \
    rm cmake-3.25.3-linux-x86_64.sh

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Create a build directory, configure the project, and build it
RUN mkdir -p build && cd build && cmake .. && make

# List the contents of the build directory to debug the executable
RUN ls -alh build/
