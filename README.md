# Bugtracker

### This is a simple bug tracker app for personal use.

## What is it look like?

Please visit [bugtracker.vakar.space](https://bugtracker.vakar.space/).

## Installation

### Additional software

Project use Java and JS technologies. So you need to install JDK11 or higher. You can find how to install JDK11 [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html). After JDK installation you should to [install Maven](https://maven.apache.org/install.html). Please also install npm and Node.js. Installation instructions you can find [here](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm).

### Downloading

You can download and unzip project or clone repository

### Setting up application.properties

You need to specify values inside curly brackets. Application use hibernate as ORM. Also be sure you register app at [facebook for developers](https://developers.facebook.com/) and get app id and secret. 

```bash

# Hibernate properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.{your-sql-database-dialect}
spring.jpa.hibernate.ddl-auto=update
# JPA Optimization
spring.jpa.open-in-view=true
# Datasource
spring.datasource.url={jdbc-database-url}
spring.datasource.username={your-username}
spring.datasource.password={your-password}
# Json serialization settings
spring.jackson.serialization.fail-on-empty-beans=false
# OAuth2 facebook authorization
spring.security.oauth2.client.registration.facebook.client-id={facebook-app-id}
spring.security.oauth2.client.registration.facebook.client-secret={facebook-secret}


```

### Build application

In project directory run one by one next commands inside terminal.

```

npm install
npm run build
mvn package

```

Last command generate bugtracker.war file inside target folder.

### Running app

You can run war file inside Tomcat server on you localhost or you should provide valid https connection on remote server. You can get free SSL Certificate on [ZeroSSL](https://zerossl.com/).

## License

[MIT](https://choosealicense.com/licenses/mit/)