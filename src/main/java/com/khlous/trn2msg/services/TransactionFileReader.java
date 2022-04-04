package com.khlous.trn2msg.services;

import com.khlous.trn2msg.dao.DAO;
import com.khlous.trn2msg.domains.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;

@Service
@Slf4j
public class TransactionFileReader {

    private final DAO dao;
    private final ApplicationArguments applicationArguments;

    public TransactionFileReader(DAO dao, ApplicationArguments applicationArguments) {
        this.dao = dao;
        this.applicationArguments = applicationArguments;
    }

    @PostConstruct
    public void run(){
        log.info("START.");
        try {
            verifyArguments();

            try {
                Notification notification = readTransactionFile();
                dao.save(notification);
            } catch (IOException e) {
                log.error(String.format("ERROR: Transaction file was not read. Output file will not be created. Exception desc: %s", e.getMessage()));
            }

            log.info("END.");
        } catch (Exception e) {
            log.error(String.format("ERROR: %s",e.getMessage()));
        }
    }

    private Notification readTransactionFile() throws IOException {
        Notification notification = new Notification();
        BufferedReader reader = new BufferedReader(getTransactionFileReader());
        log.info("Start of file conversion.");

        String line;
        char[] transactionLine;
        while ((line = reader.readLine()) != null) {
            log.info("Start of line processing.");
            transactionLine = line.toCharArray();
            notification.addMessage(transactionLine);
            log.info("End of line processing.");
        }

        log.info("End of file conversion.");
        return notification;
    }

    private FileReader getTransactionFileReader() throws FileNotFoundException {
        File transactionFile = new File(applicationArguments.getSourceArgs()[0]);
        if (transactionFile.exists() && transactionFile.isFile()) {
            return new FileReader(transactionFile);
        } else {
            throw new FileNotFoundException(String.format("Transaction file \"%s\" is not found or cannot be opened.", applicationArguments.getSourceArgs()[0]));
        }
    }

    private void verifyArguments() {
        log.info(String.format("Arguments verification begin. Arguments provided: \"%s\"", Arrays.toString(applicationArguments.getSourceArgs())));

        if (applicationArguments.getSourceArgs().length < 1 || applicationArguments.getSourceArgs()[0].isEmpty()) {
            throw new IllegalArgumentException(String.format("No transaction file provided. Arguments provided: \"%s\"", Arrays.toString(applicationArguments.getSourceArgs())));
        }

        log.info("Arguments verified.");
    }
}
