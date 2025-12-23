# Ehelper

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).

## Development

Update your local database connection in `application.yml` or create your own `application-local.yml` file to override settings for development.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be added in the VM options of the Run Configuration after enabling this property in "Modify options".

In addition to the Spring Boot application, the DevServer must also be started - for this [Node.js](https://nodejs.org/) version 24 is required. On first usage and after updates the dependencies have to be installed:

```
npm install
```

The DevServer can be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:8081`. All changes to the templates and JS/CSS files are immediately visible in the browser.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Node.js is automatically downloaded using the `frontend-maven-plugin` and the final JS/CSS files are integrated into the jar.

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/ehelper-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=com.dialltay/ehelper
```
server:
error:
whitelabel:
enabled: false
#This makes it possible to build the correct URL even
#if the application is behind a reverse proxy.
forward-headers-strategy: framework

spring:
jackson:
date-format: com.fasterxml.jackson.databind.util.StdDateFormat
time-zone: UTC

#Azure configuration
cloud:
azure:
keyvault:
secret:
property-source-enabled: true
property-sources[0]:
endpoint:

# config mailer
mail:
properties:
mail:
smtp:
auth: true
starttls:
enable: true
connectiontimeout: 5000
timeout: 3000
writetimeout: 5000

#HikariCP configuration
datasource:
hikari:
minimum-idle: 5 # Minimum number of idle connections in the pool (avoid the cold start)
maximum-pool-size: 40
max-lifetime: 900000 # avoid the connection leak (15 minutes)
transaction-isolation: TRANSACTION_READ_COMMITTED
auto-commit: false # Required for JPA
connection-timeout: 30000 # 30 seconds (avoid request blocking)
validation-timeout: 5000 # 5 seconds (avoid request blocking)
leak-detection-threshold: 60000 # 60 seconds (notify the close connection)
data-source-properties:
useServerPrepStmts: false # Keep false for PostgreSQL
cachePrepStmts: true
prepStmtCacheSize: 500
prepStmtCacheSqlLimit: 1024

# Flyway configuration
flyway:
locations: "classpath:db/migration,classpath:dev/db/migration,classpath:local/db/migration"

jpa:
# Hibernate properties
hibernate:
ddl-auto: validate #using flyway
show-sql: false
open-in-view: false # Open-Session in View (OSIV)
properties:
hibernate:
id:
optimizer.pooled.preferred: pooled-lo
connection:
'[provider_disables_autocommit]': true
query:
'[in_clause_parameter_padding]': true # Postgres IN clause padding
'[fail_on_pagination_over_collection_fetch]': true
'[plan_cache_max_size]': 4096
jdbc:
'[time_zone]': UTC
'[batch_size]': 15
'[order_inserts]': true
'[order_updates]': true

# Monitoring config
management:
endpoints:
info:
enabled: true
web:
exposure:
include: "*"
security:
enabled: true
web:
exposure:
include: health,info,env,metrics
metrics:
enable:
hikaricp: true # HikariCP metrics

#storage, containers names
storage:
blob:
container:
companies: ${companyContainer}
users: ${userContainer}

#Logging config
logging:
level:
org:
hibernate: error

super:
admin:
email: ${superAdminEmail}
tel: ${superAdminTel}
birthDate: ${superAdminBirthDate}
firstname: ${superAdminFirstname}
lastname: ${superAdminLastname}

jwt:
secret: ${jwtSecret}

# Mailer config
email:
sender: ${emailSender}
nimba:
api:
id: ${nimbaServiceId}
key: ${nimbaSmsKey}
sender: ${nimbaSmsSender}

# corns
purge:
cron:
expression: 0 0 5 * * ?  # 5am every day