# **Healthcare Management Project**

Branch: OSGI

## **Getting Started**

### Dependencies

* Java SE 11
* Apache Felix
* Apache Maven
* Docker (containerised)

### **Prerequisites**

1. **Docker**

   As the Java requirements are quite outdated, the program is packaged inside a Docker container for ease of use.

---

### **How to Run**

1. Go to (`cd`) the root directory with the `Dockerfile`.
2. Build the project container 

   ```bash
   docker build -t test/osgi .
   ```

   This will copy the source files to `/usr/osgi` in the container and build `.jar` bundle files for each of the bundle project inside the `target` directory. For example, the project `./booking-api` generates the bundle `.jar` at `./booking-api/target/booking-api-1.0-SNAPSHOT.jar`.

3. Run the project container and connect to the `bash` terminal in container:

   ```bash
   docker run -it test/osgi bash
   ```

4. Start Felix interactive shell

   ```bash
   java -jar ./bin/felix.jar
   ```

5. Install the built bundles and note the bundle ID:

    ```
    [in Felix terminal, shown symbol is g!]
    install File:/usr/osgi/booking-api/target/booking-api-1.0-SNAPSHOT.jar
    install File:/usr/osgi/booking-client/target/booking-client-1.0-SNAPSHOT.jar
    install File:/usr/osgi/booking-impl/target/booking-impl-1.0-SNAPSHOT.jar
    ```

    A sample terminal might be showing:
    
    ```
    g! install File:/usr/osgi/booking-api/target/booking-api-1.0-SNAPSHOT.jar
    Bundle ID: 8
    g! install File:/usr/osgi/booking-client/target/booking-client-1.0-SNAPSHOT.jar
    Bundle ID: 9
    g! install File:/usr/osgi/booking-impl/target/booking-impl-1.0-SNAPSHOT.jar
    Bundle ID: 10
    ```

6. Load the bundles into OSGI in order:

    ```
    [in Felix terminal, shown symbol is g!]
    start 8
    start 9
    start 10
    ```

7. View the terminal output after loading `booking-impl`.