# Use the official node runtime as a parent image
FROM node:14 as build-stage

# Set the working directory in the container to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Install the dependencies and build the app
RUN npm install && npm run build

# Use nginx to serve the build
FROM nginx as production-stage

# Copy the build app to nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 81

CMD ["nginx", "-g", "daemon off;"]
