# Healthcare Management Project

This repository contains two components:
1. **Spring Boot Component**: A RESTful API built using Spring Boot.
2. **OSGi Component**: A modular service-based application.

---

## **Getting Started**

### Prerequisites

Ensure you have this Java version installed:
- **Java 23** 
Ensure you have these extensions installed:
- Spring Initializr Java Support 
- Spring Boot Tools 
- Spring Boot Extension pack
Refer `https://www.geeksforgeeks.org/how-to-build-spring-boot-project-in-vscode/`

---

### How To Run
1. Clone this repository
2. Build the project with this command `mvn clean install` or `mvn clean compile`
3. Run and debug the project
4. Go to `http://localhost:8080/main` to check your code
5. If you made any updates just click Ctrl+S on the project and reload the website
6. Hmm..........................

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.0/reference/web/servlet.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.0/reference/using/devtools.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.0/specification/configuration-metadata/annotation-processor.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

