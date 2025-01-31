# Use a Debian-based OpenJDK image
FROM openjdk:17-slim

# Install required packages
RUN apt-get update && apt-get install -y wget unzip dos2unix && rm -rf /var/lib/apt/lists/*

# Set up Android SDK environment variables
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${PATH}

# Create the Android SDK directory
RUN mkdir -p ${ANDROID_HOME}

# Download and install the Android SDK command-line tools
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O /tmp/commandlinetools.zip && \
    unzip -q /tmp/commandlinetools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    rm /tmp/commandlinetools.zip

# Move the extracted tools to the correct directory structure
RUN mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest

# Accept licenses and install SDK components
RUN yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses && \
    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Set working directory to the app folder
WORKDIR /app

# Copy project files into the container
COPY . .

# Convert gradlew to Unix line endings
RUN dos2unix ./gradlew && chmod +x ./gradlew


# Run Gradle to build the APK
RUN ./gradlew assembleDebug

# APK output path
CMD ["bash", "-c", "find /app -name '*.apk'"]