# Int.io

Intio is a command line application or CLI for generating a list of locals from their
longitude and latitude to a central location, which remains specified in code for security purposes.


## Installation

Using the the build tool [maven](https://maven.apache.org/install.html) to build the application and [java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) to execute.

```bash
mvn clean install
```

## Usage
From the project root directory you can use a precomplied jar with

```bash
java -jar intio-1.0.jar
```

and follow instructions within the command line from there

## Compiling and Testing

Testing can be completed with the following
```bash
mvn test
```

Compilation is via
```bash
mvn clean install
```
or
```bash
mvn clean package
```


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Known Issues
-Sometimes, and I cannot figure out why. The dialog window for selecting files is not
brought to foreground. Just take a peek behind the currently opened windows.
