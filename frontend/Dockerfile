# Use the official node runtime as a parent image
FROM node:14 as build-stage

# Set the working directory in the container to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Install the dependencies and build the app
RUN npm install
RUN npm run build

# Use nginx to serve the build
FROM nginx:latest as production-stage

# Set nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build app to nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy the custom nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 81
EXPOSE 81

CMD ["nginx", "-g", "daemon off;"]
