FROM node:18-alpine

WORKDIR /app

COPY package.json package-lock.json* ./
RUN npm install

COPY . .

EXPOSE 8080

CMD sh -c "npx tsx server.js & sleep 2 && npx tsx test_client.js & wait"