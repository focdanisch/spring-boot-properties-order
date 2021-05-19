# Content

This project showcases the changed (possibly wrong) sort ordering of Spring Boot 2.4.5 after an update from Spring Boot 2.3.10.RELEASE.

# How to reproduce

In the root folder, run the tests with

```bash
mvn clean install
```

By default, the project uses Spring Boot 2.4.5, which causes both tests to fail. If you want to run the tests using the previous Spring Boot 2.3.10.RELEASE, just move the comment in the main pom.xml appropriately (the old version is commented out by default) to run the same tests with the previous version. With that version, the tests pass.
