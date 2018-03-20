package com.ef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class Parser {

    private static final String startDateFormat = "yyyy-MM-dd.HH:mm:ss";

    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    public static void main(String[] args) {
        if (validCommandLineArgs(new SimpleCommandLinePropertySource(args))) {
            SpringApplication.run(Parser.class, args);
        }
    }

    private static boolean validCommandLineArgs(PropertySource propertySource) {
        String duration = propertySource.getProperty("duration").toString();
        int threshold = getThresholdValue(propertySource.getProperty("threshold").toString());

        // Verify duration and threshold
        if (!duration.equals(Duration.HOURLY.desc) && !duration.equals(Duration.DAILY.desc)) {
            log.error("Duration can only be " + Duration.HOURLY.desc + " or " + Duration.DAILY.desc);
            return false;
        } else if (duration.equals(Duration.HOURLY.desc) && (threshold <= 0 || threshold > 200)) {
            log.error("Threshold must be greater than 0.  200 is the hourly limit");
            return false;
        } else if (duration.equals(Duration.DAILY.desc) && (threshold <= 0 || threshold > 500)) {
            log.error("Threshold must be greater than 0.  500 is the daily limit");
            return false;
        }

        // Verify startDate
        if (!isValidStartDate(propertySource.getProperty("startDate").toString())) {
            log.error("startDate format must be " + startDateFormat);
            return false;
        }

        // Verify accesslog
        if (!isFileExist(propertySource.getProperty("accesslog").toString())) {
            log.error("accesslog file does not exist");
            return false;
        }

        return true;
    }

    private static boolean isFileExist(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return false;
        }
        return true;
    }

    private static int getThresholdValue(String threshold) {
        try {
            return Integer.parseInt(threshold);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    private static boolean isValidStartDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(startDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
