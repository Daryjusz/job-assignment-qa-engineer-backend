# Select base image here, for example:
FROM node:14.17.3

# Setup project here, for example:
WORKDIR /app
ADD package*.json ./
RUN npm ci
ADD . .

# Run tests here, for example:
CMD ["npm", "run", "start"]
