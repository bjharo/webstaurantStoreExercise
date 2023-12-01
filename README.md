# README - Selenium Exercise for WebstaurantStore

## Requirements

- macOS Sonoma or Windows 11
  - Older versions may work but have not been tested
- Java JDK 17
  - This was project was developed using [OpenJDK](https://openjdk.org/)
- Maven
  - Tested with version 3.9.5, other version may or may not work
- Google Chrome
  - The most recent version preferably installed in its default location
- Java and Maven must be properly setup in your PATH

## Major Libraries Used

- [Selenium 4](https://github.com/SeleniumHQ/selenium)
- [JUnit 5](https://github.com/junit-team/junit5)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

## Executing Tests

1. Open this terminal and clone this repo to you local machine.
2. Go into the folder where you cloned the repo.
3. You can run tests with one of two commands
   1. `mvn clean test` - Runs tests in visible web browsers
   2. `mvn clean test -Pheadless` - Runs tests in headless browsers
   3. If you wish to generate a html report, you can replace `test` with `surefire-report:report`

The project is set to run tests in parallel so when you execute tests, multiple browsers will open.

If you chose to use the html report option, you can file the report in the `target/site` folder under the name `surefire-report.html`.

This project contains two tests one of which, as of 2023-11-30, is expected to fail.

