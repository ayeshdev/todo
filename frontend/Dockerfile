# Stage 1: Build Angular App
FROM node:22.14.0-alpine3.21 AS build

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the Angular app
COPY . .

# Build the Angular app
RUN npm run build --output-path=dist

# Stage 2: Serve using Nginx
FROM nginx:latest

# Copy the built Angular app to Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
