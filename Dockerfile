FROM amazoncorretto:11-alpine-jdk

COPY . /
RUN ./mvnw clean package
EXPOSE 5000

ENTRYPOINT ["docker-entrypoint.sh"]
