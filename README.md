# trace-id-example

This is an example Spring Boot app using kotlin, configured with Spring Cloud Sleuth to annotate logs with trace IDs.

It exposes a `GET /?name=` endpoint producing logs and output with trace IDs set.

* running the app locally: `./gradlew bootRun`
* pushing the app to cloud foundry: `./gradlew build && cf push trace-id-example-1 -p ./build/libs/trace-id-example-0.0.1-SNAPSHOT.jar -m 512M`
