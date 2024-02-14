FROM openjdk:17-jdk-alpine
COPY ./target/Bluemoon_API-0.0.1.jar app.jar

ENV JDBC_URL=jdbc:postgresql://localhost:54320/bluemoon
ENV DB_USER_USERNAME=bluemoon_admin
ENV DB_USER_PASSWORD=ur%]SEmRPcvMqfB;2xs>!

ENTRYPOINT [                                                        \
                "java", "-jar", "/app.jar",                         \
                "--spring.datasource.url=$JDBC_URL",                \
                "--spring.datasource.username=$DB_USER_USERNAME",   \
                "--spring.datasource.password=$DB_USER_PASSWORD"    \
           ]

# docker build -t bluemoonrestapi .
# docker run -p 8080:8080 bluemoonrestapi
# docker run -p 8080:8080 -e JDBC_URL=url DB_USER_USERNAME=postgres DB_USER_PASSWORD=secret bluemoonrestapi
# docker run -p 8080:8080 -e JDBC_URL=jdbc:postgresql://172.17.0.2:54320/postgres -e DB_USER_USERNAME=postgres -e DB_USER_PASSWORD=secret bluemoonrestapi