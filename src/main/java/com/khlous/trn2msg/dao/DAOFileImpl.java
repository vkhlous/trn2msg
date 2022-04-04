package com.khlous.trn2msg.dao;

import com.khlous.trn2msg.common.PrintWriterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
@Slf4j
public class DAOFileImpl implements DAO{

    private final PrintWriterFactory pwFactory;

    public DAOFileImpl(PrintWriterFactory pwFactory) {
        this.pwFactory = pwFactory;
    }

    @Override
    public void save(Object object) throws IllegalArgumentException {
        log.info(String.format("Saving object \"%s\".", object.getClass().getSimpleName()));
        if (object.toString() != null) {
            PrintWriter pw = pwFactory.getPrintWriter();
            pw.print(object);
            pw.close();
        } else {
            throw new IllegalArgumentException("Object string is empty! Notification file will be empty.");
        }

    }
}
