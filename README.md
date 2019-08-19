# Access Log Parser (Spring Batch)

This is a parser for web access log file.  It uses spring-batch for importing the access log to mysql.

## Build

Run `mvn clean package` to build the project. The build artifacts will be stored in the `target/` directory.

## Setup

This project uses mysql as data store.  Run `resources/createSchema.sql` to create the `parser_db` database.

## Run

Run the following in the command prompt:

`java -jar /path/to/parser.jar com.ef.Parser --accesslog=/path/to/access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200`

The log file is located in `resources/access.log`.

The format for `startDate` is `yyyy-MM-dd.HH:mm:ss`.

`duration` can either be `hourly` or `daily`.

The threshold limit for `hourly` is 200, and for `daily` is 500.

The example command line will return all IPs that made more than 200 requests in 1 hour starting from 2017-01-01.15:00:00. 