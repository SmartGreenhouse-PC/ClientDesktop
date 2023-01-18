# Greenhouse Client Desktop Application
This repository contains the code for the Greenhouse Desktop application.

## Deployment instruction
To deploy the application you must have installed on your host:

- the 11 version of the openjdk: [https://openjdk.org/](https://openjdk.org/)
- Gradle: [https://gradle.org/](https://gradle.org/)
- Smart greenhouse server: [Server](https://github.com/SmartGreenhouse-22-23/Server)

Before executing the application you need to change the configuration file, in the directory `src/main/resources` setting as `host` the ip address of the host hosting the Server application

Once you have verified that you have everything you need to run the application, you need to move to the ClientDesktop directory and launch the `gradle shadowsJar` task, via the following command:

      ./gradlew shadowJar

At this point, the .jar file has been made, and in order to run it, you need to move to the sub-directory `/buld/libs` and run the command:

      java -jar jar_name.jar

**NOTE**: replace jar_name.jar with the filename.jar in the directory indicated.
