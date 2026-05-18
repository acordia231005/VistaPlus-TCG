# Build stage
FROM node:24-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production

# Serve stage (Node.js SSR server instead of Nginx)
FROM node:24-alpine
WORKDIR /app
COPY --from=builder /app/dist/FrontedVistaPlus /app/dist/FrontedVistaPlus
COPY --from=builder /app/package*.json /app/
RUN npm install --only=production

EXPOSE 4000
ENV PORT=4000
CMD ["node", "dist/FrontedVistaPlus/server/server.mjs"]