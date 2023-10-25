# Use the official node runtime as a parent image
FROM node:14 as build-stage

# Set the working directory in the container to /app
WORKDIR /app

# Copy the package.json and package-lock.json before other files
# Utilize Docker cache to save re-installing dependencies if unchanged
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the current directory contents into the container at /app
COPY . .

# Build the app
RUN npm run build

# Use nginx to serve the build
FROM nginx:latest as production-stage

# Set nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build app to nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Expose port 81
EXPOSE 81

CMD ["nginx", "-g", "daemon off;"]
