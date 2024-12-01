FROM gcc:latest

# Install necessary tools
RUN apt-get update && apt-get install -y cmake

# Copy the entire project into the container
COPY . /usr/src/mytest

# Set working directory to the project root
WORKDIR /usr/src/mytest
COPY . .

# Ensure a clean build directory
RUN rm -rf build && mkdir build
RUN ls -l /usr/src/mytest

# Switch to the build directory
WORKDIR /usr/src/mytest/build

# Run CMake and make
RUN cmake .. && make

# Set the default command to execute the tests
CMD ["./runTests"]
