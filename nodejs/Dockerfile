# Use an official Node.js runtime as the base image
FROM node:16

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the package.json and package-lock.json from the build context
COPY package*.json ./

# Install the application dependencies
RUN npm install

# Copy the rest of the application code from the nodejs folder
COPY nodejs/ .

# Expose the port the app runs on
EXPOSE 3000

RUN npm install wait-on

CMD ["wait-on", "tcp://mongo:27017", "&&", "node", "nodejs/app.js"]

