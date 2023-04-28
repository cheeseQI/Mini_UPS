FROM openjdk:8-jdk-alpine

COPY hw5_proj.jar /app/
COPY truck.sql /docker-entrypoint-initdb.d/1-truck.sql
COPY user_table.sql /docker-entrypoint-initdb.d/2-user_table.sql
COPY package.sql /docker-entrypoint-initdb.d/3-package.sql

RUN apk update && apk add postgresql && apk add postgresql-client && rm -rf /var/cache/apk/*

EXPOSE 7474
EXPOSE 8080
EXPOSE 5432

CMD ["java", "-jar", "/app/hw5_proj.jar"]
