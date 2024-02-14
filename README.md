# BLUE MOON REST API

---

## BUILDING THE API

---

### MAVEN

The project can be built using **Maven**. Use the following command to build the application:

```bash
mvn clean install
```
It will create this folder structure, with the **compiled application** within.

### DOCKER

The application can be containerized using Docker. Use the provided Dockerfile to build the **Docker image** with the following command:
```bash
docker build -t bluemoonrestapi .
```
After building the docker image you can **create a container** with the following command:
```bash
docker run -p 8080:8080 bluemoonrestapi
```
You can also specify **enviroment variables** to set the **API URL** or the administrator account used internally by the app.
```bash
docker run -p 8080:8080 -e JDBC_URL=url DB_USER_USERNAME=postgres DB_USER_PASSWORD=secret bluemoonrestapi
```

For example, let's say our **host** machine IP adress is **192.168.1.129** and we are running the REST API locally. We would include the following URL:

-e JDBC_URL=jdbc:postgresql://192.168.1.129:5432/postgres

By default, the connection data are the follwing:

**Username**: bluemoon_admin \
**Password**: ur%]SEmRPcvMqfB;2xs>!