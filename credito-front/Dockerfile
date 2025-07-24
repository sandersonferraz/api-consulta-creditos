# Etapa de build
FROM node:20-alpine AS builder
WORKDIR /app
COPY . .
RUN npm install && npm run build --configuration=production

# Etapa final (serve conteúdo estático)
FROM node:20-alpine
WORKDIR /app
RUN npm install -g http-server
COPY --from=builder /app/dist/credito-front/browser /app
EXPOSE 4200
CMD ["http-server", ".", "-p", "4200", "-c-1", "--fallback", "index.html"]
