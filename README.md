---

# **Healthcare Management Project**

This repository contains two components:

1. **Spring Boot Component**: A RESTful API built using Spring Boot.
2. **OSGi Component**: A modular service-based application.

---

## **Getting Started**

### **Prerequisites**

1. **Java Version**

   - Ensure you have **Java 17** installed. _(Note: Update from "Java 23" if the target is Java 17 as used in the IDE configuration in the image.)_  
     You can download Java from the [Official Java Website](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **VS Code Extensions**  
   Install the following extensions in Visual Studio Code for a smooth development experience:

   - **Spring Initializr Java Support**
   - **Spring Boot Tools**
   - **Spring Boot Extension Pack**

   Refer to this guide: [Build Spring Boot Project in VS Code](https://www.geeksforgeeks.org/how-to-build-spring-boot-project-in-vscode/).

---

### **Database Setup**

Create a **MySQL database** named `healthcare`.  
You can use the following SQL command to create the database:

```sql
CREATE DATABASE healthcare;
```

---

### **Configure `application.properties`**

1. Update your **`src/main/resources/application.properties`** with the following configuration:

```properties
spring.application.name=healthcare

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=update

# Email Configuration (Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Server Configuration
server.port=8080

```

2. Replace:
   - `your_database_username` and `your_database_password` with your MySQL credentials.
   - `your_email@example.com` and `your_smtp_password` with your SMTP credentials.

---

### **How to Run**

1. Build the project:

   ```bash
   mvn clean install
   ```

2. Run the project:

   ```bash
   mvn spring-boot:run
   ```

3. Application URL at [http://localhost:8080](http://localhost:8080).

---

## **Reference Documentation**

For further reference, please consider the following resources:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html)
- [Spring Web Documentation](https://docs.spring.io/spring-boot/3.4.0/reference/web/servlet.html)
- [Spring Boot DevTools Documentation](https://docs.spring.io/spring-boot/3.4.0/reference/using/devtools.html)

---

### **Useful Guides**

The following guides provide examples of features used in the project:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST Services with Spring](https://spring.io/guides/tutorials/rest/)

---

### **Maven Parent Overrides**

Due to Maven's inheritance model, elements such as `<license>` and `<developers>` from the parent POM are overridden. If switching to a different parent and you want these elements inherited, you may need to remove the empty overrides in the project POM.

---
