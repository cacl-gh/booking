FROM node:12.7-alpine AS build
WORKDIR /src/app
COPY package.json ./
RUN npm install -g @angular/cli@1.7.4
COPY . .
EXPOSE 8081
RUN ng serve --open
