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

-On MacOS and Windows, the previous file selection persists. No matter what happens, seems to be
a shortcoming of JavaFx. See details [here](https://stackoverflow.com/questions/12736880/clear-jfilechooser-selection-after-adding-files-to-a-jlist)
Have implemented a fix for windows, but unable to test as I use a Mac. A good alternative is to
just read from a command line argument the file in question.

-Switched to JFrame and filename filtering via FileDialog as a result of above. JavaSwing feels ancient.

