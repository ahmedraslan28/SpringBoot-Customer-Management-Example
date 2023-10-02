FROM node:20-alpine 

ARG api_base_url

WORKDIR /app 

COPY . . 

RUN echo "REACT_APP_API_BASE_URL = ${api_base_url}" > .env

RUN npm install --silent 

EXPOSE 3000

CMD [ "npm", "start" ]



