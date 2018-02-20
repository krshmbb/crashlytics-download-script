# Crashlytics download script

This script downloads all the sessions and corresponding crash logs from Crashlytics/Fabric using the [Selenium Chrome web driver for Java][seleniumhq].


## Project setup

- Before you will need to download the [ChromeDriver executable][chrome-webdriver] that WebDriver uses to control Chrome and update the value of `PATH_TO_CHROME_WEBDRIVER` in `FabricConstants.java`.
Example:
    ``` java
    public static final String PATH_TO_CHROME_WEBDRIVER = "/Users/USERNAME/chromedriver/chromedriver";
    ```
- Further, in _FabricConstants.java_ configure the Fabric credentials and paths.


## Gradle build

To compile the project from the shell execute the following command:

``` bash
$ ./gradlew clean assemble
```

You will then find the `crashlytics-download-script-VERSION.jar` file in the `build/libs` folder.


## Dependencies

Selenium depends on the following transitive dependencies:

- com.google.code.gson:gson
- com.google.guava:guava
- org.apache.commons:commons-exec
- org.apache.httpcomponents:httpclient



[seleniumhq]: http://www.seleniumhq.org/download
[chrome-webdriver]: https://sites.google.com/a/chromium.org/chromedriver/downloads
