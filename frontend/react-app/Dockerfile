FROM node:20-alpine 

ARG REACT_APP_API_BASE_URL

WORKDIR /app 

ENV REACT_APP_API_BASE_URL=$REACT_APP_API_BASE_URL

COPY . .

RUN npm install --silent

EXPOSE 3000

CMD [ "npm", "start" ]



