# Stage 1: Build Angular App
FROM node:22.14.0-alpine3.21 AS build
# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular application
RUN npm run build --prod

# Step 2: Serve the application using Nginx
FROM nginx:alpine

# Copy the built application from the previous stage
COPY --from=build /app/dist/todo/browser /usr/share/nginx/html

# Create a custom Nginx configuration file
RUN echo 'server { \
    listen 4200; \
    server_name localhost; \
    location / { \
        root /usr/share/nginx/html; \
        try_files $uri $uri/ /index.html; \
    } \
}' > /etc/nginx/conf.d/default.conf

# Expose port 8080
EXPOSE 4200

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
