package com.khlous.trn2msg.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Service
@Slf4j
public class PrintWriterFactory {

    private final ApplicationArguments applicationArguments;

    public PrintWriterFactory(ApplicationArguments applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    public PrintWriter getPrintWriter(){
        try {
            if (applicationArguments.getSourceArgs().length == 2 && applicationArguments.getSourceArgs()[1].isEmpty() == false) {
                PrintWriter pw = new PrintWriter(applicationArguments.getSourceArgs()[1]);
                log.info(String.format("Output will be written to file \"%s\".", applicationArguments.getSourceArgs()[1]));
                return pw;
            }
        } catch (FileNotFoundException e) {
            log.error(String.format("ERROR: File \"%s\" can not be opened.", applicationArguments.getSourceArgs()[1]));
        }

        log.info("Output will be written to standard output.");
        return new PrintWriter(System.out);
    }
}
